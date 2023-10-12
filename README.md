# Plasom-reunion

## วิธีการติดตั้งโปรแกรม

1. เข้าไปที่ส่วนของ Release
2. เข้าไปโหลดไฟล์ ZIP
3. นำไฟล์ ZIP ไปแตกไฟล์เพื่อที่จะได้ไฟล์ .jar ออกมา
4. รันไฟล์ .jar

## Group Members

| ชื่อ-นามสกุล                 | รหัสนิสิต | GitHub Username | หมู่ |
|:-----------------------------| :----------------: | :----------------: | ----------------:|
| **นางสาวจินดามณี ศรีหะรัญ**  | **6510405385** | **JindamaneeSri** |**12**|
| **นายภูมิระพี เสริญวณิชกุล** | **6510405750** | **Homiez09** |**12**|
| **นายปิยะ กองศรี**           | **6510450666**| **OwlVi**     |**200**|
| **นางสาวปุญญิศา ธัญญพงษ์**   | **6510450674** |**MalaMing** |**200**|

## การจัดวางโครงสร้างไฟล์

- data
  - ban-team.csv
  - ban-user.csv
  - chat-history.csv
  - event-activity-list.csv
  - event-list.csv
  - join-event.csv
  - join-team.csv
  - team-activity.csv
  - team-list.csv
  - user-list.csv
- image
  - events
  - user-avatar
- src
  - main
    - java
      - cs211.project
        - componentControllers
          - alertBox
            - DeleteTeamAlertBoxController
            - JoinTeamAlertBoxController
          - sideBarControllers
            - SideBarTeamController
          - teamControllers
            - manageTeamController
              - ManageMemberTeamListController
              - ManageTeamComponentController
              - UnbanUserTeamController
            - manageTeamsController
              - ManageTeamsBoxController
            - teamboxControllers
              - TeamBox1Controller
              - TeamBox2Controller
            - AvatarProfileController
            - CardEventController
            - CardJoinTeamController
            - CardMyEventController
            - CardUpcoming
            - ChangePasswordController
            - CreateActivityController
            - CreateTeamController
            - EventTileController
            - navbarController
            - navbarGuestController
            - OwnerEventController
            - topBarController
            - UserCardProfileController
            - UsersEventController
        - controllers
          - admin
            - AdminDashboardController
          - team
            - ManageTeamController
            - MessageBoxController
            - TeamActivityController
            - TeamChatController
          - AllEventListController
          - CreateEventController
          - EditActivityController
          - EventPageController
          - HomeController
          - JoinTeamController
          - MyEventsController
          - ProfileDeveloperController
          - SelectTeamController
          - SettingPageController
          - SignInController
          - SignUpController
          - UserProfileController
          - WelcomeController
        - cs211661project
          - HelloApplication
        - models
          - collections
            - ActivityList
            - ActivityTeamList
            - ChatHistory
            - EventList
            - TeamList
            - UserList
          - Activity
          - ActivityTeam
          - Chat
          - Event
          - Team
          - User
        - services
          - team
            - LoadSideBarComponent
            - LoadUserCardProfileComponent
          - ActivityListDataSource
          - ActivityTeamListDataSource
          - BanTeamMap
          - BanUser
          - BlockArrowKeyFromTabPane
          - BorderImagView
          - ChatHistoryDataSource
          - CreateProfileCircle
          - Datasource
          - EventListDataSource
          - FXRouter
          - GenerateRandomID
          - ImagePathFormat
          - JoinEventMap
          - JoinTeamMap
          - LoadCardEventComponent
          - LoadCardEventUpcomingForAuth
          - LoadNavbarComponent
          - LoadTopBarComponent
          - RoleUserComparator
          - StatusUserComparator
          - TableCellCenter
          - TeamListDataSource
          - TimestampChatComparator
          - UploadMessageThread
          - UserListDataSource
          - UsernameUserComparator
        - Main
    - module-info.java
  - resources
    - css211.project.views
      - components
        - alert-box
          - delete-team-alert-box.fxml
          - join-team-alert-box.fxml
        - side-bar
          - sidebar-team.fxml
        - team
          - chats
            - message-box.fxml
          - manage-team
            - manage-member-team-list-1.fxml
            - manage-member-team-list-2.fxml
            - manage-team.fxml
            - unban-user-team.fxml
          - manage-teams
            - team-box-list.fxml
          - menu
            - leader-menu.fxml
        - team-box
          - team-box-1.fxml
          - team-box-2.fxml
      - avatar-profile.fxml
      - card-event.fxml
      - card-join-team.fxml
      - card-my-event.fxml
      - card-upcoming.fxml
      - change-password.fxml
      - create-event-activity.fxml
      - create-team.fxml
      - event-tile.fxml
      - event-tile-new.fxml
      - navbar.fxml
      - navbar-guest.fxml
      - owner-event.fxml
      - profile-card.fxml
      - topbar.fxml
      - user-card-profile.fxml
      - users-event.fxml
    - admin-dashboard.fxml
    - all-events.fxml
    - create-new-event.fxml
    - develop-profile.fxml
    - edit-event-activity.fxml
    - event-view.fxml
    - home.fxml
    - join-team.fxml
    - my-event.fxml
    - select-team.fxml
    - setting.fxml
    - sign-in.fxml
    - sign-up.fxml
    - user-profile.fxml
    - welcome.fxml
    - welcome-new.fxml
    - css
      - team
        - create-team.css
        - team-activity-table.css
        - team-chat.css
      - card-event.css
      - create-event.css
      - event.css
      - home.css
      - host.css
      - my-events.css
      - style.css
      - style2.css
      - welcome.css
    - images
      - backgrounds
        - login
          - sign_event_bg1.png
          - sign_event_bg2.png
        - welcome
          - wecome_new_bg.png
          - welcome_bg.png
      - eventcomponent
        - 01 align center.png
        - clock end.png
        - clock start.png
        - clock.jpg
        - Icon.png
        - tag.png
        - user-group.png
        - user.png
      - events
        - calendar.png
        - calendar-day.png
        - event-default.png
        - event-default-auth.png
        - Group 310.png
        - Leave.png
        - manage.png
        - person.png
        - pin.png
        - pinsmall.png
        - user-group.png
        - Vector-1.png
        - Vector.png
      - home
        - blackgrid.png
        - event2.png
        - whitegrid.png
      - hostevent
        - Icon.png
        - sort-ascending.png
        - sort-descending.png
        -sort.png
      - icons
        - activity
          - back_arrow.png
          - chat.png
          - chat_hover.png
          - create.png
          - edit.png
          - next_arrow.png
          - searh.png
          - trash.png
        - admin-sidebar
          - chart-bar.png
          - cog.png
          - folder.png
          - hover_setting.png
          - hover_user_log.png
          - logout.png
          - setting.png
          - user-group.png
          - user_log.png
        - alert
          - correct.png
          - question.png
          - wrong.png
        - chat
          - send-message-button.png
        - join-team
          - calendar.png
          - search_bar.png
          - users_group.png
          - users_group_orange.png
        - login
          - status
            - offline_active.png
            - online_active.png
          - change_image.png
          - checkbox_password.png
          - contact_field.png
          - hide_password.png
          - password_field.png
          - show_password.png
          - username_field.png
        - navbar
          - clipboard-list.png
          - exit.png
          - mail.png
          - question-mark-circle.png
          - search.png
          - user-group.png
        - select-team
          - create_icon.png
          - create_icon_hover.png
          - point_icon.png
          - setting_icon.png
          - setting_icon_hover.png
          - sort_icon.png
        - setting
          - cog.png
          - switch-bodyOff.png
          - switch-bodyOn.png
          - Switch.png
        - team
          - create-team
            - check_box.png
          - side-bar
            - activity.png
            - dashboard.png
            - home.png
            - hover_activity.png
            - hover_dashboard.png
            - hover_home.png
            - hover_manage_team.png
            - hover_team_chat.png
            - leave_team.png
            - manage_team.png
            - team_chat.png
          - sortIcon.png
        - team-box
          - bookmark
            - bookmark_icon.png
            - un_bookmark_icon.png
          - role
            - Leader.png
            - Member.png
            - Owner.png
          - switch-view
            - team_box_1.png
            - team_box_2.png
          - active_icon.png
            - dot_icon.png
            - face_icon.png
            - people_icon.png
        - user-profile
          - avatar
            - avatar.png
            - click_avatar.png
            - hover_avatar.png
          - device
            - click_device.png
            - device.png
            - hover_action_device.png
            - hover_device.png
            - white_device.png
          - check_box.png
          - iconEllipse.png
          - iconFrame.png
          - iconProfile.png
      - login
        - event0_test.jpg
        - event1_test.jpg
        - event2_test.jpg
        - event3_test.jpg
      - logo
        - logo_black.png
        - logo_orange.png
        - logo_white.png
      - profile
        - default-avatar
          - default0.png
          - default1.png
          - default2.png
          - default3.png
          - default4.png
          - default5.png
          - default6.png
          - default7.png
          - default8.png
          - default9.png
        - develop
          - ging.png
          - man.jpg
          - ming.jpg
          - phum.jpg
        - edit-profile
          - avatar_icon.png
        - sign-in
          - sign-in-avatar.png

## ตัวอย่างข้อมูลผู้ใช้ระบบ

| Username     | Password   | Role  |
|:-------------|:-----------|:------|
| admin        | admin      | Admin |
| Anny         | Testunit0! | User  |
| mingmmie     | Testunit0! | User  |
| HomieZ09     | Testunit0! | User  |
| minggggggg   | Testunit0! | User  |
| ging95       | Testunit0! | User  |
| PinkPPanther | Testunit0! | User  |
| Ming         | Testunit0! | User  |
| phunyisa.T   | Testunit0! | User  |
| user123      | Testunit0! | User  |
| kikie11      | Testunit0! | User  |
| mrowlvi      | Testunit0! | User  |
| sele         | Testunit0! | User  |
| queasyguy    | Testunit0! | User  |
| mk           | Testunit0! | User  |


## สรุปรายละเอียดการพัฒนาในการส่งงานแต่ละครั้ง

### ครั้งที่ 1 ( ส่งก่อนวันที่ 11 ส.ค. 2566 17:00 น.)
> + วางแผนปรึกษาหารือเรื่องของธีมและชื่อโปรแกรมที่จะใช้
> + ออกแบบ Model Class ที่จะต้องใช้ในโปรแกรมและแบ่งหน้าที่ในการสร้างหน้าต่าง UI กับแต่ละคน
> + ออกแบบหน้าต่าง UI เบื้องต้นลงใน Figma แล้วเสนอแลกเปลี่ยนความคิดเห็น เพื่อปรับปรุงและแก้ไข
> + เริ่มทำหน้าต่าง ๆ ของโปรแกรม หน้ายินดีต้อนรับ หน้าล็อกอิน หน้า Developer หน้าโฮมและหน้าตั้งค่าเพื่อทดสอบปุ่มต่าง ๆ และ ตรวจสอบ Ui ให้ตรงตามแบบที่ร่างไว้
> + เตรียม Model Class ที่คิดไว้คร่าว ๆ และ Service อ่านไฟล์ลงในโปรเจ็กต์


### ครั้งที่ 2 ( ส่งก่อนวันที่ 1 ก.ย. 2566 17:00 น.)
>
>

### ครั้งที่ 3 ( ส่งก่อนวันที่ 22 ก.ย. 2566 17:00 น.)
>
>

### ครั้งที่ 4 ( ส่งก่อนวันที่ 13 ต.ค. 2566 17:00 น.)
>
>
