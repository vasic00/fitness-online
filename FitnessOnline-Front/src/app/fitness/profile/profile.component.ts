import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { User } from '../../model/User';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { Message } from '../../model/Message';
import { MessageService } from '../services/message.service';
import { MessageDialogComponent } from '../message-dialog/message-dialog.component';
import { AccountSettingsDialogComponent } from '../account-settings-dialog/account-settings-dialog.component';
import { UserService } from '../../account/user.service';
import { BrowseComponent } from '../browse/browse.component';
import { SubscriptionsDialogComponent } from '../subscriptions-dialog/subscriptions-dialog.component';
import { Category } from '../../model/Category';
import { CategoryService } from '../services/category.service';
import { FeedbackService } from '../services/feedback.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {

  user: User = new User();

  @ViewChild('browseComponent')
  browseComponent!: BrowseComponent;

  @Output()
  updateBrowseData: EventEmitter<string> = new EventEmitter();

  constructor(private router: Router,
    public dialog: MatDialog,
    private messageService: MessageService,
    private userService: UserService,
    private categoryService: CategoryService,
    private feedbackService: FeedbackService) {

  }

  ngOnInit() {
    this.user = JSON.parse(sessionStorage.getItem("user")!);

  }

  contactAdvisors() {
    const dialogRef: any = this.dialog.open(MessageDialogComponent, {
      width: '500px',
      height: '360px',
      data: {
        receiver: "advisors"
      }
    });
    dialogRef.afterClosed().subscribe((result: string) => {
      if (result !== undefined && result !== null && result !== '') {
        console.log(result);
        let newMessage: Message = new Message();
        newMessage.content = result;
        newMessage.sender = this.user?.firstname + " " + this.user?.lastname;
        newMessage.receiverId = -1;
        newMessage.senderId = this.user!.id;
        newMessage.unread = true;
        this.messageService.sendMessage(newMessage).subscribe({
          next: (data: any) => this.feedbackService.showMessage("Message sent!"),
          error: (err: any) => console.log("Error sending message.")
        })
      }
    })
  }

  manageAccount() {
    let dialogRef = this.dialog.open(AccountSettingsDialogComponent, {
      maxWidth: '80vw',
    });
    dialogRef.afterClosed().subscribe({
      next: (data: any) => {
        let user: User = data as User;
        this.userService.update(user).subscribe({
          next: (updatedUser: User) => {
            updatedUser.token = this.user.token;
            this.user = updatedUser;
            sessionStorage.setItem("user", JSON.stringify(updatedUser));
            console.log("Uspjesno azuriranje korisnika");
            this.browseComponent.fetch();
            this.updateBrowseData.emit("update");
          },
          error: (err: any) => {
            console.log("Greska u azuriranju korisnika");
          }
        });
      },
      error: (data: any) => {
        console.log("Greska u azuriranju podataka korisnika.");
      }
    })
  }

  logout() {
    sessionStorage.clear();
    this.router.navigate(['auth']);
  }

  openSubscriptions() {
    const dialogRef: any = this.dialog.open(SubscriptionsDialogComponent, {
      width: "300px",
    });
    dialogRef.afterClosed().subscribe((data: any) => {
      let categoriesToUnsub: Category[] = data.toUnsub as Category[];
      categoriesToUnsub.forEach((c) => {
        this.categoryService.deleteSubscription(c.id).subscribe({
          error: (err: any) => console.log("Error deleting subscriptions!")
        });
      });
      let categoriesToSub: Category[] = data.toSub as Category[];
      categoriesToSub.forEach((c) => {
        this.categoryService.addSubscription(c.id).subscribe({
          error: (err: any) => console.log("Error adding subscriptions!")
        });
      })
    })
  }

}
