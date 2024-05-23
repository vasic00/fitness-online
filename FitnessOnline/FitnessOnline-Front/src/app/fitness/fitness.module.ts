import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FitnessRoutingModule } from './fitness-routing.module';
import { HomepageComponent } from './homepage/homepage.component';
import { ProgramInfoComponent } from './program-info/program-info.component';

// import { CardComponent } from './card/card.component';
import { MaterialModule } from '../material/material.module';
// import { ShopComponent } from './shop/shop.component';
// import { ProfileComponent } from './profile/profile.component';
// import { PurchaseHistoryComponent } from './purchase-history/purchase-history.component';
import { MatPaginator } from '@angular/material/paginator';
import { FormsModule } from '@angular/forms';
import { AntdModule } from '../antd/antd.module';
import { BrowseComponent } from './browse/browse.component';
import { CardComponent } from './card/card.component';
import { HistoryComponent } from './history/history.component';
import { BuyingDialogComponent } from './buying-dialog/buying-dialog.component';
import { AddProgramComponent } from './add-program/add-program.component';
import { CommentBoxComponent } from './comment-box/comment-box.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MessageDialogComponent } from './message-dialog/message-dialog.component';
import { ProfileComponent } from './profile/profile.component';
import { AccountSettingsDialogComponent } from './account-settings-dialog/account-settings-dialog.component';
import { InboxComponent } from './inbox/inbox.component';
import { SubscriptionsDialogComponent } from './subscriptions-dialog/subscriptions-dialog.component';
import { ExercisesRecommendationComponent } from './exercises-recommendation/exercises-recommendation.component';
import { ActivityLogComponent } from './activity-log/activity-log.component';
import { ExerciseDialogComponent } from './exercise-dialog/exercise-dialog.component';


@NgModule({
  declarations: [
    HomepageComponent,
    ProgramInfoComponent,
    BrowseComponent,
    CardComponent,
    HistoryComponent,
    BuyingDialogComponent,
    AddProgramComponent,
    CommentBoxComponent,
    MessageDialogComponent,
    ProfileComponent,
    AccountSettingsDialogComponent,
    InboxComponent,
    SubscriptionsDialogComponent,
    ExercisesRecommendationComponent,
    ActivityLogComponent,
    ExerciseDialogComponent,
  ],
  imports: [
    CommonModule,
    FitnessRoutingModule,
    MaterialModule,
    FormsModule,
    AntdModule,
    MatPaginator,
    ReactiveFormsModule,
  ],
})
export class FitnessModule { }
