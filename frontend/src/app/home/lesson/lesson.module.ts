import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LessonRoutingModule } from './lesson-routing.module';
import { MatCardModule } from '@angular/material/card';
import { LessonComponent } from './lesson.component';
import { ListComponent } from './list/list.component';
import { DetailComponent } from './detail/detail.component';
import { SectionComponent } from './section/section.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatTableModule} from '@angular/material/table';
import { ListVocablessComponent } from './list-vocabless/list-vocabless.component';
import { ListGrammarlessComponent } from './list-grammarless/list-grammarless.component';
import { ListKanjilessComponent } from './list-kanjiless/list-kanjiless.component';
import { ListReadingComponent } from './list-reading/list-reading.component';
import { ListWritingComponent } from './list-writing/list-writing.component';
import { ListListeningComponent } from './list-listening/list-listening.component';
import { ListKaiwaComponent } from './list-kaiwa/list-kaiwa.component';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatTabsModule} from '@angular/material/tabs';
import {MatIconModule} from '@angular/material/icon';
import { NgxPaginationModule } from 'ngx-pagination';
import { SharedModule } from 'src/app/shared/shared.module';
import { FormsModule } from '@angular/forms'; // Import the FormsModule

@NgModule({
  declarations: [
    LessonComponent,
    ListComponent,
    DetailComponent,
    SectionComponent,
    ListVocablessComponent,
    ListGrammarlessComponent,
    ListKanjilessComponent,
    ListReadingComponent,
    ListWritingComponent,
    ListListeningComponent,
    ListKaiwaComponent,
  ],
  imports: [
    CommonModule,
    LessonRoutingModule,
    MatCardModule,
    MatPaginatorModule,
    MatTableModule,
    MatExpansionModule,
    MatTabsModule,
    MatIconModule,
    NgxPaginationModule,
    SharedModule,
    FormsModule
  ]
})
export class LessonModule { }
