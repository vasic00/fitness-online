import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MessageService } from '../services/message.service';
import { Message } from '../../model/Message';
import { MatDialog } from '@angular/material/dialog';
import { MessageDialogComponent } from '../message-dialog/message-dialog.component';
import { FeedbackService } from '../services/feedback.service';

@Component({
  selector: 'app-inbox',
  templateUrl: './inbox.component.html',
  styleUrl: './inbox.component.css'
})
export class InboxComponent implements OnInit {

  selectedType: string = "unread";
  contains: string = "";
  messages: Message[] = [];

  @Output()
  updateInboxIcon: EventEmitter<string> = new EventEmitter();

  constructor(private messageService: MessageService,
    public dialog: MatDialog,
    private feedbackService: FeedbackService) {

  }

  ngOnInit() {
    this.fetch();
  }

  fetch() {
    let key = "-";
    if (this.contains !== null && this.contains !== undefined && this.contains !== "")
      key = this.contains;
    let unread = "-";
    if (this.selectedType !== null && this.selectedType !== undefined && this.selectedType !== "") {
      if (this.selectedType === "unread")
        unread = "true";
      else if (this.selectedType === "read")
        unread = "false";
    }
    this.messageService.getReceivedByUser(unread, key).subscribe({
      next: (data: any) => {
        this.messages = data as Message[];
        this.messages.sort((m1, m2) => m2.id - m1.id);
        this.checkIfThereAreUnreadMessages();
      },
      error: (err: any) => {
        console.log("Greska u dohvatanju poruka");
      }
    })
  }

  markAsRead(message: Message) {
    if (this.selectedType === "unread" && message.unread === false)
      this.messages.splice(this.messages.indexOf(message), 1);
    if (message.unread === true)
      this.messageService.readMessage(message.id).subscribe((data: any) => {
        console.log("Message read!");
        this.messageService.getReceivedByUser("true", "-").subscribe((data: any) => {
          this.checkIfThereAreUnreadMessages();
        })
      });
    message.unread = false;
    message.display = !message.display;
  }

  reply(message: Message, event: Event) {
    this.stopPropagation(event);
    const dialogRef: any = this.dialog.open(MessageDialogComponent, {
      width: '500px',
      height: '360px',
      data: {
        receiver: message.sender
      }
    });
    dialogRef.afterClosed().subscribe((result: string) => {
      if (result !== undefined && result !== null && result !== '') {
        console.log(result);
        let newMessage: Message = new Message();
        newMessage.content = result;
        newMessage.receiverId = message.senderId;
        newMessage.senderId = message.receiverId;
        newMessage.unread = true;
        this.messageService.sendMessage(newMessage).subscribe({
          next: (data: any) => this.feedbackService.showMessage("Message sent!"),
          error: (err: any) => console.log("Error sending message.")
        })
      }
    })
  }

  deleteMessage(message: Message, event: Event) {
    this.stopPropagation(event);
    this.messageService.deleteMessage(message.id).subscribe((data: any) => {
      console.log("Message deletion.");
      this.messages.splice(this.messages.indexOf(message), 1);
      this.checkIfThereAreUnreadMessages();
    });
  }

  stopPropagation(event: Event) {
    event.stopPropagation();
  }

  checkIfThereAreUnreadMessages() {
    this.messageService.getReceivedByUser("true", "-").subscribe((data: any) => {
      if ((data as Message[]).length === 0)
        this.updateInboxIcon.emit("allread");
      else this.updateInboxIcon.emit("unread");
    })
  }
}