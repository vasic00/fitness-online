export class Message {
    id: number = -1;
    content: string = '';
    unread: boolean = true;
    senderId: number = -1;
    receiverId: number = -1;
    sender: string = '';
    timeOfSending: string = '';
    display: boolean = false;
}