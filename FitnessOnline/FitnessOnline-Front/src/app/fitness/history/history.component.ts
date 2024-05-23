import { Component, OnInit } from '@angular/core';
import { PurchaseService } from '../services/purchase.service';
import { Purchase } from '../../model/Purchase';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent implements OnInit {

  purchaseList: Array<Purchase> = [];
  displayedColumns: string[] = ['paymentType', 'time', 'programCategory', 'programName', 'programPrice']


  constructor(private service: PurchaseService) {

  }

  ngOnInit() {
    this.service.getByBuyer().subscribe({
      next: (data: Array<Purchase>) => { this.purchaseList = data; console.log(this.purchaseList) },
      error: (err: any) => console.log("error u dohvatanju purchase"),
    })
  }
}
