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
  ]
})
export class LessonModule { }
