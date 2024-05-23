import { Component, Input, OnInit } from '@angular/core';
import { Program } from '../../model/Program';
import { ActivatedRoute, Router } from '@angular/router';
import { ProgramService } from '../services/program.service';
import { environment } from '../../config/environment';
import { User } from '../../model/User';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrl: './card.component.css'
})
export class CardComponent implements OnInit {

  @Input('program') program: Program = new Program();

  public image: string = '';
  public hasImage = true;
  @Input('participated') participated: boolean = false;

  constructor(private router: Router,
    private service: ProgramService,
    private route: ActivatedRoute) {
  }

  ngOnInit() {
    if (this.program.images && this.program.images.length > 0) {
      this.image = environment.baseURL + environment.imagesPath + '/' + this.program.images[0]?.id;
    }
    else this.hasImage = false;
    // this.service.checkIfParticipated(this.program.id!).subscribe({
    //   next: (data: boolean) => {
    //     if (data) {
    //       this.participated = ".participated";
    //     }
    //   }
    // });
  }

  getInfo() {
    this.router.navigate(['program'], { relativeTo: this.route, state: this.program });
  }

  endsIn(): string {
    return this.service.endsIn(Date.parse(this.program.ending));
  }
}
