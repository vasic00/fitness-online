import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-rss',
  templateUrl: './rss.component.html',
  styleUrl: './rss.component.css'
})
export class RssComponent implements OnInit {

  rssData: any;

  constructor(private httpClient: HttpClient) {
  }

  ngOnInit() {
    this.httpClient.get("http://localhost:3000/rss-feed").subscribe(data => {
      this.rssData = data
    });
  }

}
