import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from './sidebar/sidebar.component';
import { BanUserComponent } from './manage-user/ban-user/ban-user.component';
import { CreateUserComponent } from './manage-user/create-user/create-user.component';
import { UserDetailComponent } from './manage-user/user-detail/user-detail.component';
import { UserListComponent } from './manage-user/user-list/user-list.component';
import { DeleteChatComponent } from './manage-chat/delete-chat/delete-chat.component';
import { ListChatComponent } from './manage-chat/list-chat/list-chat.component';
import { ChatConversationComponent } from './manage-chat/chat-conversation/chat-conversation.component';
import { ListPostComponent } from './manage-post/list-post/list-post.component';
import { PostDetailComponent } from './manage-post/post-detail/post-detail.component';
import { ListLessonComponent } from './manage-lesson/list-lesson/list-lesson.component';
import { LessonDetailComponent } from './manage-lesson/lesson-detail/lesson-detail.component';
import { AdminRoutingModule } from './admin-routing.module';



@NgModule({
  declarations: [
    SidebarComponent,
    BanUserComponent,
    CreateUserComponent,
    UserDetailComponent,
    UserListComponent,
    DeleteChatComponent,
    ListChatComponent,
    ChatConversationComponent,
    ListPostComponent,
    PostDetailComponent,
    ListLessonComponent,
    LessonDetailComponent
  ],
  imports: [
    // CommonModule
    AdminRoutingModule
  ]
})
export class AdminModule { }
