# Plasom-reunion

## วิธีการติดตั้งโปรแกรม

1. เข้าไปที่ส่วนของ Release
2. ทำการดาวโหลดไฟล์ Zip
3. นําไฟล์ Zip มาแตกไฟล์ให้ได้ไฟล์ .jar
4. ทำการรันไฟล์ .jar

## Group Members

| ชื่อ-นามสกุล                 |   รหัสนิสิต    |  GitHub Username  |    หมู่ |
|:-----------------------------|:--------------:|:-----------------:|--------:|
| **นางสาวจินดามณี ศรีหะรัญ**  | **6510405385** | **JindamaneeSri** |  **12** |
| **นายภูมิระพี เสริญวณิชกุล** | **6510405750** |   **Homiez09**    |  **12** |
| **นายปิยะ กองศรี**           | **6510450666** |     **OwlVi**     | **200** |
| **นางสาวปุญญิศา ธัญญพงษ์**   | **6510450674** |   **MalaMing**    | **200** |

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
    - 2023-10-10_1696933405354.jpg
    - 2023-10-10_1696945107839.png
    - 2023-10-11_1696958256883.jpg
    - 2023-10-11_1697000484221.png
    - 2023-10-11_1697000581604.png
    - 2023-10-11_1697001425138.png
    - 2023-10-11_1697025932586.jpg
    - 2023-10-12_1697083521018.jpg
  - user-avatar
    - user-DEV010245_mrowlvi.png
    - user-MOq928049_kikie11.png
    - user-ZKt668157_mingmmie.png
- src
  - main
    - java
      - cs211.project
        - controllers
          - component
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
          - view
            - admin
              - AdminDashboardController
            - auth
              - SignInController
              - SignUpController
            - event
              - AllEventListController
              - CreateEventController
              - EditActivityController
              - EventPageController
              - MyEventsController
            - team
              - AllTeamController
              - JoinTeamController
              - ManageTeamController
              - MessageBoxController
              - TeamActivityController
              - TeamChatController
            - HomeController
            - ProfileDeveloperController
            - SettingPageController
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
        - event-tile-new.fxml
        - event-tile.fxml
        - navbar-guest.fxml
        - navbar.fxml
        - owner-event.fxml
        - profile-card.fxml
        - topbar.fxml
        - user-card-profile.fxml
        - users-event.fxml
      - team
        - team-activity.fxml
        - team-chat.fxml
        - team-manage.fxml
        - team-template.fxml
      - admin-dashboard.fxml
      - all-events.fxml
      - all-team.fxml
      - create-new-event.fxml
      - develop-profile.fxml
      - edit-event-activity.fxml
      - event-view.fxml
      - home.fxml
      - join-team.fxml
      - my-event.fxml
      - setting.fxml
      - sign-in.fxml
      - sign-up.fxml
      - user-profile.fxml
      - welcome-new.fxml
      - welcome.fxml
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
        - sort.png
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

### ตัวอย่างข้อมูลผู้ใช้ระบบ

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

## สรุปรายละเอียดการพัฒนาในการส่งงานแต่ละครั้ง ##

### ครั้งที่ 1 ( ส่งก่อนวันที่ 11 ส.ค. 2566 17:00 น.)
#### ภูมิระพี เสริญวณิชกุล 6510405750 (HomieZ09)
> - เพิ่ม UI ของหน้า Welcome Page, Developer Profile Page
> - เพิ่ม Navigation Bar Component สำหรับของ User และ Guest
>   - ของ User จะแสดงรูป Avatar Profile และแถบทำทางไปยังหน้า Home และ My Event
>   - ของ Guest จะแสดงแถบนำทางให้ไปยัง Sign-In, Sing-Up
> - เพิ่ม Top Bar Component ที่ใช้ในหน้า Welcome, Develop, Instruction
> - ลิงก์ปุ่มไปยังหน้าต่างๆใน Welcome Page

### ครั้งที่ 2 ( ส่งก่อนวันที่ 1 ก.ย. 2566 17:00 น.)
#### ภูมิระพี เสริญวณิชกุล 6510405750 (HomieZ09)
> - เพิ่ม Team Model และ TeamList ที่ใช้สำหรับเก็บข้อมูลของ Team Model
> - เพิ่ม TeamListDatasourceHardcode เพื่อใช้ทดสอบการแสดงผลของทีม
> - เพิ่มหน้า Admin Dashboard ที่สามารถดูการเข้าสู่ระบบของ User ได้ โดยจะเรียงจากผู้ที่เข้าสู่ระบบคนล่าสุดเป็นคนแรกโดยไม่นับ Admin
> - เพิ่มเงื่อนไขให้ Navigation Bar เพื่อทำการเช็กว่าเป็น User หรือเป็น Guest
> - ย้ายการเรียกใช้ Navigation Bar ของหลายๆหน้ามาเขียนเป็น Service เพื่อให้หน้าอื่นๆสามารถเรียกใช้ได้ง่าย
> - แก้ไข UI ของหน้า Welcome Page
> - แก้ไข UI ของ Top Bar Component

### ครั้งที่ 3 ( ส่งก่อนวันที่ 22 ก.ย. 2566 17:00 น.)
#### ภูมิระพี เสริญวณิชกุล 6510405750 (HomieZ09)
> ได้รับมอบหมายให้ทำหน้า All-Team ต่อจากความก้าวหน้าครั้งที่สองของหมิง (ทำร่วมกับ MalaMing)
> - เพิ่ม Service ImagePathFormat เพื่อใช้ Format image path ของ User(เพราะเนื่องจากว่า user บางคนใช้ Default Avatar บางคนใช้การอัปโหลดรูปจากเครื่อง)
> - แก้ไข All-Team
>   - เพิ่มการส่งข้อมูลเข้าไปใน Team-Box-Component ในหน้า All-Team (100%)
>     - ชื่อทีม
>     - Role (ของ User)
>     - Participant // ทำเสร็จแล้วในโครงงานที่เสร็จสมบูรณ์
>     - Online Team Users // ทำเสร็จแล้วในโครงงานที่เสร็จสมบูรณ์
>     - Bookmark
>   - เพิ่มระบบ Filter
>   - เพิ่มการกด bookmark ทีมที่ชื่นชอบ
>   - เพิ่ม Manage Teams แบบด่วน (จะเห็นทีมในมุมมองของลิสลงมาทำให้จัดการได้เร็วและง่ายขึ้นเหมาะสำหรับเมื่อต้องการ	จัดการหลายๆทีมพร้อมๆกัน) สามารถกดได้จากรูปเฟืองข้างๆ Filter
>   - เพิ่ม Sort Teams ตามเวลาที่สร้าง
> - แก้ไข Admin Dashboard
>   - เพิ่มแสดงสถานะออนไลน์ของ Users
>   - เพิ่มแสดงสถานะออฟไลน์ของ Users
>   - เพิ่มแสดงจำนวนอีเวนต์ทั้งหมดและที่จบลง
>   - แก้ไข UI หน้า Admin Dashboard
> - แก้ไข Team Model
>   - เพิ่มเมธอดเปลี่ยนเวลาเป็น Timestamp และเมธอดเปลี่ยนกลับ

### ครั้งที่ 4 ( ส่งก่อนวันที่ 13 ต.ค. 2566 17:00 น.)
#### ภูมิระพี เสริญวณิชกุล 6510405750 (HomieZ09)
> - เพิ่ม ActivityTeam Model และ ActivityTeamList ที่ใช้สำหรับเก็บข้อมูลของ ActivityTeam Model (โดย Inheritance มาจาก Activity Model)
> - เพิ่มหน้า Team Activity
>   - เพิ่ม UI ของหน้า Team Activity
>   - เพิ่มการสร้าง Activity
>   - เพิ่มการแก้ไข Activity ด้วยการดับเบิลคลิก
>   - เพิ่มการลบ Activity
>   - เพิ่มปุ่มกดไปยัง Activity ถัดไป/ก่อนหน้า
>   - เพิ่ม Search Bar สำหรับค้นหา Activity
>   - เพิ่มการแสดงผลของการ Search
>   - เพิ่มการลิงก์ไปยังช่องแชตของ Activity นั้น
> - เพิ่มหน้า Chat Activity
>   - เพิ่ม UI ของหน้า Chat Activity
>   - เพิ่ม Group Activity ที่สามารถเข้าร่วมแชตได้
>   - เพิ่มการกด Enter สำหรับการส่งข้อความ
>   - เพิ่มเช็กถ้าไม่มีการพิมพ์ใดๆในช่องแชตจะไม่สามารถส่งข้อความได้ปุ่มจะถูก Disable
>   - เพิ่ม Service สำหรับ Write Data ลงใน CSV (Optimize ด้วย Runnable)
> - แก้ไขหน้า Manage Team
>   - เพิ่ม Unban สำหรับการปลดแบน User ที่เคยแบนไปในทีม
> - แก้ไขหน้า Home
>   - เพิ่ม Event Card Component สำหรับแสดง Event ที่เป็น Upcoming Event, New Event, และ Recommend Event
> - แก้ไขหน้า All Event
>   - แก้ไข Card Component ให้มีขนาดที่เหมาะสม
> - แก้ไขหน้า Sign-In, Sign-Up
>   - เพิ่ม Upcoming Event Card Component สำหรับแสดง Event ที่เป็น Upcoming Event