import { Component, OnInit } from '@angular/core';
import { Program } from '../../model/Program';
import { Router } from '@angular/router';
import { SpecificAttribute } from '../../model/SpecificAttribute';
import { AttributeService } from '../services/attribute.service';
import { CategoryService } from '../services/category.service';
import { Category } from '../../model/Category';
import { ProgramService } from '../services/program.service';
import { User } from '../../model/User';
import { PurchaseService } from '../services/purchase.service';
import { Purchase } from '../../model/Purchase';
import { MatDialog } from '@angular/material/dialog';
import { BuyingDialogComponent } from '../buying-dialog/buying-dialog.component';
import { Image } from '../../model/Image';
import { environment } from '../../config/environment';
import { Comment } from '../../model/Comment';
import { CreatorInfo } from '../../model/CreatorInfo';
import { CommentService } from '../services/comment.service';
import { MessageDialogComponent } from '../message-dialog/message-dialog.component';
import { Message } from '../../model/Message';
import { MessageService } from '../services/message.service';
import { FeedbackService } from '../services/feedback.service';

@Component({
  selector: 'app-program-info',
  templateUrl: './program-info.component.html',
  styleUrl: './program-info.component.css'
})
export class ProgramInfoComponent implements OnInit {

  program: Program = new Program();
  specificAttributes: SpecificAttribute[] = [];
  user: User | null = null;
  participateDisabled = true;
  deleteDisabled = true;
  images: string[] = [];
  newCommentText: string = "";
  guest: boolean = true;

  constructor(private router: Router,
    private attributeService: AttributeService,
    private categoryService: CategoryService,
    private purchaseService: PurchaseService,
    private programService: ProgramService,
    public dialog: MatDialog,
    private feedbackService: FeedbackService,
    private commentService: CommentService,
    private messageService: MessageService) {
    if (this.router.getCurrentNavigation()?.extras.state) {
      this.program = this.router.getCurrentNavigation()?.extras.state as Program;
      categoryService.getCategoryByName(this.program.category).subscribe({
        next: (cat: Category) => {
          if (cat) {
            attributeService.getSAByCategory(cat.id).subscribe((sas: SpecificAttribute[]) => {
              console.log(sas);
              console.log(this.program.attributes);
              this.specificAttributes = sas;
            })
          }
        },
        error: (err: any) => {
          console.log("error pri dohvatanju kategorije")
        }
      });
      // attributeService.getSAByCategory(this.program.category.)
    }
  }
  ngOnInit(): void {
    if (sessionStorage.getItem("user") !== null) {
      this.guest = false;
      this.user = JSON.parse(sessionStorage.getItem("user")!);
      if (this.user!.id === this.program.creator.id) {
        //placeholder
        console.log("yes, deletable");
        this.deleteDisabled = false;
        this.participateDisabled = true;
      }
      else {
        this.programService.checkIfParticipated(this.program.id!).subscribe({
          next: (data: boolean) => {
            if (!data)
              this.participateDisabled = false;
          }
        });
      }
    }
    this.program.images.forEach((img: Image) => {
      this.images.push(environment.baseURL + environment.imagesPath + '/' + img.id);
    })
  }

  findByAttributeId(id: number): string {
    let name: string = '';
    this.specificAttributes.forEach((sa: SpecificAttribute) => {
      if (sa.id === id) {
        name = sa.name;
      }
    })
    return name;
  }

  participate() {
    const dialogRef: any = this.dialog.open(BuyingDialogComponent, {
      width: '300px',
    });
    dialogRef.afterClosed().subscribe((result: string) => {
      if (result != null && result != '') {
        let purchase: Purchase = new Purchase();
        purchase.paymentType = result;
        purchase.userId = this.user!.id;
        purchase.programId = this.program.id as number;
        purchase.programPrice = this.program.price;
        purchase.programCategory = this.program.category;
        purchase.programName = this.program.name;
        this.purchaseService.participate(purchase).subscribe((value: any) => {
          console.log(value);
          this.participateDisabled = true;
          this.feedbackService.showMessage("Successful purchase!");
        })
      }
    })
  }

  deleteProgram() {
    if (window.confirm("Are you sure you want to delete this program?")) {
      this.programService.delete(this.program.id!).subscribe({
        next: (data: any) => {
          this.deleteDisabled = true;
          this.feedbackService.showMessage("Successful deletion!");
          this.router.navigate(['fitness']);
        }
      })
    }
  }

  endsIn(): string {
    return this.programService.endsIn(Date.parse(this.program.ending));
  }

  returnToBrowsing(): void {
    this.router.navigate(['fitness']);
  }

  postComment(): void {
    if (this.newCommentText !== "") {
      let comment: Comment = new Comment();
      comment.content = this.newCommentText;
      if (this.user!.avatar !== '')
        comment.creatorAvatar = this.user!.avatar;
      else comment.creatorAvatar = '';
      // let creatorInfo: CreatorInfo = new CreatorInfo();
      // creatorInfo.id = this.user!.id;
      // creatorInfo.info = this.user!.firstname + " " + this.user!.lastname;
      // comment.creatorInfo = creatorInfo;
      comment.creatorId = this.user!.id;
      comment.creatorInfo = this.user!.firstname + " " + this.user!.lastname;
      comment.programId = this.program.id!;
      comment.time = new Date().toLocaleString();
      this.commentService.postComment(comment).subscribe({
        next: (data: any) => {
          this.program.comments.push(comment);
          this.newCommentText = "";
        },
        error: (err: any) => {
          console.log("Greska u postavljanju komentara");
        }
      });
    }
  }

  message() {
    const dialogRef: any = this.dialog.open(MessageDialogComponent, {
      width: '500px',
      height: '360px',
      data: {
        receiver: this.program.creator.info
      }
    });
    dialogRef.afterClosed().subscribe((result: string) => {
      if (result !== undefined && result !== null && result !== '') {
        console.log(result);
        let newMessage: Message = new Message();
        newMessage.content = result;
        newMessage.sender = this.user?.firstname + " " + this.user?.lastname;
        newMessage.receiverId = this.program.creator.id;
        newMessage.senderId = this.user!.id;
        newMessage.unread = true;
        this.messageService.sendMessage(newMessage).subscribe({
          next: (data: any) => this.feedbackService.showMessage("Message sent!"),
          error: (err: any) => console.log("Error sending message.")
        })
      }
    })
  }

}
