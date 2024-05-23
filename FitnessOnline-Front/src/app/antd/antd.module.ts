import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzCheckboxModule } from 'ng-zorro-antd/checkbox';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzCarouselModule } from 'ng-zorro-antd/carousel';
import { NzAvatarModule } from 'ng-zorro-antd/avatar';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzMessageModule } from 'ng-zorro-antd/message';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { NzUploadModule } from 'ng-zorro-antd/upload';
import { NzModalModule } from 'ng-zorro-antd/modal';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    NzFormModule,
    ReactiveFormsModule,
    FormsModule,
    NzInputModule,
    NzButtonModule,
    NzCheckboxModule,
    NzSelectModule,
    NzCarouselModule,
    NzAvatarModule,
    NzIconModule,
    NzMessageModule,
    NzSpinModule,
    NzUploadModule,
    NzModalModule,
  ],
  exports: [
    ReactiveFormsModule,
    FormsModule,
    NzInputModule,
    NzButtonModule,
    NzCheckboxModule,
    NzFormModule,
    NzSelectModule,
    NzCarouselModule,
    NzAvatarModule,
    NzIconModule,
    NzMessageModule,
    NzSpinModule,
    NzUploadModule,
    NzModalModule,
  ],
})
export class AntdModule {}
