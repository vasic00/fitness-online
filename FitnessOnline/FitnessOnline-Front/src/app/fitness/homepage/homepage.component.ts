import { Component, ViewChild } from '@angular/core';
import { BrowseComponent } from '../browse/browse.component';
import { ProfileComponent } from '../profile/profile.component';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.css'
})
export class HomepageComponent {
  tabIndex: number = 0;
  guest: boolean = true;

  public inboxIcon: string = "mail";

  @ViewChild('browse')
  browseComponent!: BrowseComponent;

  @ViewChild('profile')
  myProfileComponent!: ProfileComponent;

  constructor() {
    if (
      sessionStorage.getItem('guest') &&
      sessionStorage.getItem('guest') === 'false'
    )
      this.guest = false;

    console.log('homepage guest-' + this.guest);
  }

  saveTab() {
    sessionStorage.setItem('tab', this.tabIndex.toString());
  }
  ngOnInit(): void {
    if (sessionStorage.getItem('tab'))
      this.tabIndex = Number(sessionStorage.getItem('tab'));
  }
  updateBrowse(update: string) {
    if (update === "update")
      this.browseComponent.fetch();
  }
  updateInboxIcon(update: string) {
    if (update === "unread")
      this.inboxIcon = "mark_email_unread";
    else this.inboxIcon = "mail";
  }

  handleRefresh(refresh: string) {
    if (refresh === "refresh") {
      this.browseComponent.fetch();
      this.myProfileComponent.browseComponent.fetch();
    }
  }
}
