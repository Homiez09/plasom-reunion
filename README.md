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
    - 2023-10-13_1697137418560.png
    - 2023-10-13_1697137794692.jpg
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
| Piya.k       | Testunit0! | User  |


## สรุปรายละเอียดการพัฒนาในการส่งงานแต่ละครั้ง ##

### ครั้งที่ 1 ( ส่งก่อนวันที่ 11 ส.ค. 2566 17:00 น.)
#### นายภูมิระพี เสริญวณิชกุล 6510405750 (HomieZ09)
> - เพิ่ม UI ของหน้า Welcome Page, Developer Profile Page
> - เพิ่ม Navigation Bar Component สำหรับของ User และ Guest
    >   - ของ User จะแสดงรูป Avatar Profile และแถบทำทางไปยังหน้า Home และ My Event
>   - ของ Guest จะแสดงแถบนำทางให้ไปยัง Sign-In, Sing-Up
> - เพิ่ม Top Bar Component ที่ใช้ในหน้า Welcome, Develop, Instruction
> - ลิงก์ปุ่มไปยังหน้าต่างๆใน Welcome Page
#### นายปิยะ กองศรี 6510450666 (OwlVi)
> + ออกแบบหน้า Setting และเสนอ Model Class ที่จะใช้ในโปรแกรม
> + ทำ Css ที่ใช้สำหรับหน้า Setting ของโปรแกรม
#### นางสาวปุญญิศา ธัญญพงษ์ 6510450674 (MalaMing)
> ออกแบบ ui ใน figma แบ่งหน้าที่ให้เพื่อนๆในทีม
> - เพิ่ม UI หน้า Sign-Up, Sign-In (โดยยังไม่มีการเก็บข้อมูลลงใน CSV)
> - เพิ่ม Show/Hide Password ในหน้า Sign-Up, Sign-In
> - เพิ่มแสดงการ์ดอีเวนต์ที่กำลังจะมาในหน้า Sign-Up, Sign-In
### ครั้งที่ 2 ( ส่งก่อนวันที่ 1 ก.ย. 2566 17:00 น.)
#### นายภูมิระพี เสริญวณิชกุล 6510405750 (HomieZ09)
> - เพิ่ม Team Model และ TeamList ที่ใช้สำหรับเก็บข้อมูลของ Team Model
> - เพิ่ม TeamListDatasourceHardcode เพื่อใช้ทดสอบการแสดงผลของทีม
> - เพิ่มหน้า Admin Dashboard ที่สามารถดูการเข้าสู่ระบบของ User ได้ โดยจะเรียงจากผู้ที่เข้าสู่ระบบคนล่าสุดเป็นคนแรกโดยไม่นับ Admin
> - เพิ่มเงื่อนไขให้ Navigation Bar เพื่อทำการเช็กว่าเป็น User หรือเป็น Guest
> - ย้ายการเรียกใช้ Navigation Bar ของหลายๆหน้ามาเขียนเป็น Service เพื่อให้หน้าอื่นๆสามารถเรียกใช้ได้ง่าย
> - แก้ไข UI ของหน้า Welcome Page
> - แก้ไข UI ของ Top Bar Component
#### นายปิยะ กองศรี 6510450666 (OwlVi)
> + สร้าง Model Class Event และ EventList ที่ใช้สำหรับเก็บข้อมูลของ Class Event
> + ออกแบบและสร้างหน้า My Eevnt ที่ใช้สำหรับดูรายการอีเวนต์ที่ User เข้าร่วม,User เป็นผู้จัดและรายการอีเวนต์ที่สิ้นสุดไปแล้ว
>   + สร้างปุ่มสำหรับกดออกจากอีเวนต์
>   + สร้างปุ่มสำหรับไปหน้าสร้างอีเวนต์
>   + สร้างปุ่มสำหรับดูอีเวนต์ที่สิ้นสุดแล้ว
> + สร้าง Component ที่ไว้ใช้สำหรับหน้า My Event เพื่อใช้ดูรายละเอียดของอีเวนต์ของ User
> + สร้าง Class EventDataSourceHardCode สำหรับทดสอบหน้า My Event
> + เพิ่มปุ่มหน้า Setting ที่เตรียมสำหรับการพัฒนาต่อ
>   + ปุ่ม Theme ที่ใช้สำหรับเปลี่ยน Theme
>   + ปุ่ม Privacy Policy สำหรับดูเอกสาร
>   + ปุ่ม Contact us สำหรับติดต่อผู้พัฒนา
>

#### นางสาวปุญญิศา ธัญญพงษ์ 6510450674 (MalaMing)
> - เพิ่ม User Model และ UserList ที่ใช้สำหรับเก็บข้อมูลของ User Model
    >  - เพิ่ม method ในการเช็ครหัสผ่าน
>   - เพิ่มการ Hash Password ด้วย BCrypt
> - เพิ่มหน้า User-Profile โดยสามารถไปได้โดยการกดที่ Navigation Bar (หน้าจัดการข้อมูลส่วนตัว)
    >  - เพิ่มการแก้ไขข้อมูล
>   - เพิ่มการแก้ไขนามแฝง (display name)
>   - เพิ่มการแก้ไขเบอร์โทรศัพท์ (contact number)
>   - เพิ่มการแก้ไข BIO (bio profile)
> - เพิ่มหน้า Select-Team (ทำร่วมกับ HomieZ09)
> - เพิ่มไฟล์ UserListDatasourceHardcode เพื่อใช้ test ระบบในการ Login
> - เพิ่มการ Login ในหน้า Sign-In
    >   - เพิ่มเช็คว่าผู้ใช้งานกรอกรหัสผ่านหรือชื่อของผู้ใช้ผิดหรือไม่ ถ้าผิดจะแจ้งเตือน Error ให้ผู้ใช้งานทราบ
> - เพิ่มการตั้งเงื่อนไขการสมัครสมาชิกในหน้า Sign-Up ว่ารหัสผ่านจะต้องมี Pattern แบบไหน
    >   - รหัสผ่านจะต้องมีความยาว 8-20 ตัวอักษร
>   - รหัสผ่านจะต้องมีตัวพิมพ์ใหญ่อย่างน้อย 1 ตัว
>   - รหัสผ่านจะต้องมีตัวพิมพ์เล็กอย่างน้อย 1 ตัว
>   - รหัสผ่านจะต้องมีอักษรพิเศษอย่างน้อง 1 ตัว
>   - รหัสผ่านจะต้องมีตัวเลขอย่างน้อย 1 ตัว
### ครั้งที่ 3 ( ส่งก่อนวันที่ 22 ก.ย. 2566 17:00 น.)
#### นายภูมิระพี เสริญวณิชกุล 6510405750 (HomieZ09)
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
#### นายปิยะ กองศรี 6510450666 (OwlVi)
> + ปรับปรุงแก้ไข Model Class Event 
>   + ให้มีการเก็บ ID เพื่อใช้สำหรับเรียกใช้ข้อมูลของ Event นั้นอย่างถูกต้อง
>   + ให้มีการเก็บ Timestamp เพื่อใช้ในการเปรียบเทียบ Event ที่ถูกสร้างขึ้นใหม่
>   + เพิ่มตัวแปร joinEvent สำหรับไว้สำหรับพัฒนาในครั้งถัดไป
>   + เพิ่มตัวแปร eventTag สำหรับแยกประเภทของ Event
> + สร้าง Class EventListDataSource สำหรับใช้ในการอ่านและเขียนบันทึกข้อมูลของ Event ทั้งหมดในไฟล์ event-list.csv
>   + มีการเพิ่มการเก็บข้อมูลของ ActivityList เข้าอีเวนต์ โดยสามารถเรียกข้อมูล Activity ของแต่ละอีเวนต์ผ่านอีเวนต์ได้เลย
>   + มีการเพิ่มการเก็บข้อมูลของ TeamList เข้าอีเวนต์ โดยสามารถเรียกข้อมูล Team ของแต่ละอีเวนต์ผ่านอีเวนต์ได้เลย
> + สร้าง Class LoadCardEventComponent ใช้สำหรับโหลด Component หน้าของ All Event และ My Event
>   + มีฟังชั่นทีทำ Animation เมื่อทำการวางเมาส์บน Component จะทำให้ซูมขึ้นมา 
> + สร้าง Class JoinEventMap สำหรับใช้ในการบันทึกข้อมูลของ User ที่เข้าร่วมอีเวนต์โดยใช้ HashMap ในการอ่านและเขียนบันทึกข้อมูลในไฟล์ join-event.csv
>   + บันทึกโดยใช้ eventID เป็น String และเป็น Key ของ HashMap มี Set เป็น Value โดยการเก็บ userId 
> + สร้าง Component ChangePass ใช้สำหรับเปลี่ยนรหัสผ่าน
> + สร้าง Component UsersEvent สำหรับดูรายชื่อผู้เข้าร่วมอีเวนต์ทั้งหมด
>   + แสดงรายละเอียดของแต่ละ User ในอีเวนต์
> + สร้าง Component OwnerEvent สำหรับดูรายการอีเวนต์ของผู้จัดอีเวนต์ทั้ง
>   + แสดงรายละเอียดเบื้องต้นของแต่ละอีเวนต์
> + ออกแบบและสร้างหน้าร่วมอีเวนต์ All Event ที่ใช้สำหรับให้ User และ Guest ดูอีเวนต์ทั้งหมดที่กำลังเกิดขึ้นและมาใหม่
>   + สร้าง Component สำหรับหน้าโชว์อีเวนต์ทั้งหมด
>     + แสดงชื่อของอีเวนต์
>     + แสดงชื่อของผู้จัดอีเวนต์
>     + แสดงจำนวนผู้เข้าร่วมอีเวนต์
>   + ปุ่ม All สำหรับดูอีเวนต์ทั้งหมดโดยเรียงตามชื่อ
>   + ปุ่ม New สำหรับดูอีเวนต์ที่สร้างใหม่เรียงตามเวลาที่สร้าง
>   + ปุ่ม Upcoming สำหรับดูอีเวนต์ที่กำลังจะเกิดขึ้นโดยเรียงตามวันที่เริ่มของอีเวนต์นั้น ๆ
>   + ปุ่ม Category สำหรับเลือก Tag ของอีเวนต์ที่เราอยากดูหรือสนใน
>   + ช่อง Search สำหรับพิมชื่อเพื่อค้นหาอีเวนต์
>   + ปุ่ม Sort สำหรับเรียงรายการของอีเวนต์ตามที่เลือก
>     + เรียงตามชื่อ A-Z
>     + เรียงตามวันที่อีเวนต์เริ่มต้น
>     + เรียงตามอีเวนต์ที่มีผู้เข้าร่วมเยอะที่สุด
>     + เรียงตามวันที่อีเวนต์สิ้นสุด
>   + ปุ่ม Create สำหรับสร้าง Event (ปุ่มนี้ Guest จะถูกปิดไว้เนื่องจากเป็นเพียงผู้เยี่ยมชม) 
> + ปรับปรุงหน้า My Event ใหม่-ลบแถบที่แสดงรายละเอียดของ User
>   + เพิ่มปุ่ม Upcoming สำหรับแสดงอีเวนต์ที่กำลังเกิดขึ้น
>   + เพิ่มปุ่ม Complete สำหรับดูอีเวนต์ที่ User เข้าร่วม,User เป็นผู้จัดอีเวนต์และ User เข้าร่วมทีมจัดอีเวนต์
>   + เพิ่มปุ่ม Owner สำหรับดูอีเวนต์ที่ User เป็นผู้จัดอีเวนต์
>   + เพิ่มปุ่ม Member สำหรับดูอีเวนต์ที่ User เข้าร่วม
>   + เพิ่มปุ่ม Staff สำหรับดูอีเวนต์ที่ User เข้าร่วมทีมจัดอีเวนต์
>   + ช่อง Search สำหรับพิมชื่อเพื่อค้นหาอีเวนต์
>   + เพิ่มปุ่ม Sort สำหรับเรียงรายการของอีเวนต์ตามที่เลือก
> + ปรับปรุงฟังชั่นบน Component ของหน้า My Event
>   + ปุ่ม Staff สำหรับไปหน้าของ Team ในอีเวนต์
>   + ปุ่ม Manage User สำหรับดูรายชื่อของ User (ปุ่มนี้จะเห็นเฉพาะผู้จัดอีเวนต์)
>   + ปุ่ม Leave สำหรับกดออกการเข้าร่วของอีเวนต์ (มีเฉพาะผู้ที่เข้าร่วมอีเวนต์)
>   + เมื่อกดที่จะไปที่หน้าของ Event View เพื่อดูรายละเอียดของอีเวนต์ที่เลือก
#### นางสาวปุญญิศา ธัญญพงษ์ 6510450674 (MalaMing)
> - เพิ่มการกด Enter ในหน้า Sign-Up, Sign-In เพื่อไปต่อ
> - เพิ่มการตรวจสอบการซ้ำกันของ Username ในตอน Sign-Up ถ้าซ้ำกันก็จะมีการแจ้งเตือน Error
> - แก้ไข User Profile
    >  - แก้ไข UI ในหน้า User Profile เล็กน้อย และมีการเพิ่มในส่วนของการแสดงผลข้อมูลต่างๆ
>  - เพิ่มการ Show/Hide เบอร์โทรศัพท์
>  - เพิ่มการเช็คค่าต่างๆแบบ Real Time
>  - เพิ่มฟีเจอร์เปลี่ยนรูปภาพ สามารถเลือกได้ว่าจะใช้ Default Avatar หรืออัพโหลดจากเครื่อง
     >    - อัพโหลด
>    - Drag and Drop
>  - เพิ่มปุ่ม Cancel เพื่อยกเลิกหรือคืนค่าเดิมถ้าหากว่าไม่ต้องการที่จะเปลี่ยนอะไรแล้ว
>  - เพิ่มการยืนยันรหัสผ่านเพื่อ Save ข้อมูล
> - แก้ไข Select-Team (ทำร่วมกับ HomieZ09)
    >   - เพิ่ม Switch View เพื่อเปลี่ยน Theme ของ Team Box
>   - เพิ่มฟีเจอร์ Manage Team (Quick Access) เพื่อให้สามารถจัดการ Users ในทีมได้อย่างรวดเร็ว โดยจะแสดงเป็นป๊อปอัพในหน้า Select-Team
>   - เพิ่มฟีเจอร์ Give Role โดยสามารถให้ Role ของ Leader แก่คนในทีมได้ 1 คน
>   - เพิ่มฟีเจอร์ Create Team
>   - เพิ่มฟีเจอร์ Delete Team
>   - เพิ่มฟีเจอร์ Leave Team

### ครั้งที่ 4 ( ส่งก่อนวันที่ 13 ต.ค. 2566 17:00 น.)
#### นายภูมิระพี เสริญวณิชกุล 6510405750 (HomieZ09)
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
#### นายปิยะ กองศรี 6510450666 (OwlVi)
> + ปรับปรุงแก้ไข Class Event 
>   + เพื่มตัวแปรสำหรับเก็บเวลาของการเปิดรับผู้เข้าร่วมอีเวนต์
>     + แก้ไขหน้า Edit และ Create อีเวนต์ให้ตั้งเวลาสำหรับเปิดรับผู้เข้าร่วมอีเวนต์
>   + เพิ่มฟังชั่น CompareTo สำหรับใช้เรียงอีเวนต์ตามชื่อเวลาบันทึกลงไฟล์
>   + เพิ่มฟังชั่นที่ใช้แปลงเวลาของ Event ที่เป็น String
>   + เพื่มการ overide ฟังชั่น equal เพื่อความสะดวกในการเปรียบเทียบ Object ( เพิ่มให้ User และ Team ด้วย)
>   + Clean up code 
> + เพิ่มการตรวจสอบข้อมูลเวลาสร้างอีเวนต์และเวลาแก้ไข
>   + ตรวจสอบวันที่และเวลาของอีเวนต์
>   + ตรวจสอบจำนวนสูงสุดของผู้เข้าร่วมให้มากกว่า 0
> + เพิ่มการ Drag and Drop ให้กับ Activity ของอีเวนต์
> + สร้าง Class BanUser
>   + ใช้สำหรับบันทึก User ที่ถูกผู้จัดอีเวนต์แบนเพื่อไม่ให้เป็นตารางกิจกรรม
> + สร้าง Class TableCellCenter
>   + ทำให้ Table Cell อยู่ตรงกลางของตาราง
> + สร้าง Class BorderImagView
>   + ตัดขอบรูปภาพให้โค้งมน
> + ปรับปรุงแก้ไขหน้าต่าง Event Page,All Event,My Event,Create Event,Edit Event
>   + แก้ไขหน้าต่าง UI แต่ละหน้าให้สวยขึ้น
>   + ปรับปรุงสีของแุ่มในแต่ละหน้าต่าง UI
> + ปรับปรุงแก้ไขหน้าต่าง UI ของ Component จากครั้งที่แล้วของ All Event และ My Event
> + ปรังปรุงแก้ไขหน้าต่าง UI ของ OwnerEvent
>   + เพิ่มปุ่ม Manage สำหรับไปหน้าแก้ไขอีเวนต์
>   + เพิ่มปุุ่ม View สำหรับไปหน้าดูรายละเอียดของอีเวนต์
>   + เพิ่มปุ่ม Delete สำหรับลบอีเวนต์
> + ปรับปรุงแก้ไขหน้าต่าง UI ของ UsersEvent
>   + เพิ่มปุ่ม Ban สำหรับแบนไม่ให้ผู้เช้าร่วมเห็นตารางกิจกรรม
>   + เพิ่มปุ่ม Unban สำหรับปลดแบน
>   + เพิ่มปุ่ม Kick สำหรับเตะผู้เข้าร่วมออก
> + เพิ่ม plugin สำหรับทำ .jar
#### นางสาวปุญญิศา ธัญญพงษ์ 6510450674 (MalaMing)
> - เพิ่มระบบ Ban User ในทีม
> - เพิ่มหน้า Manage Team
>   - เพิ่มการจัดการ User ในทีม
>   - เพิ่มฟีเจอร์แก้ไขทีม
> - เพิ่มหน้า Join Team
>   - เพิ่ม Card Join Team Component เพื่อใช้แสดงข้อมูลของทีมที่สามารถเข้าร่วมได้
>   - เพิ่ม Search Bar สำหรับค้นหาทีม
>   - เพิ่มการแสดงผลหลังจากการค้นหาว่าเจอผลลัพธ์เท่าไหร่
>   - เพิ่มเงื่อนไขการตรวจสอบก่อนเข้าทีม
>     - ตรวจสอบว่า User มีการเข้าทีมอยู่แล้วหรือไม่
>     - ตรวจสอบว่า User ถูกแบนหรือไม่
>     - ตรวจสอบว่า Team เต็มหรือไม่
>     - ตรวจสอบว่า Team ปิดรับสมัครไปแล้ว
>     - เพิ่ม Alert Box Component เพื่อใข้แสดงการแจ้งเตือนในรูปแบบต่างๆตามที่กล่าวข้างต้น
> - เพิ่ม Change Password Component สำหรับเปลี่ยนรหัสผ่าน
>   - แก้ไขหน้า Setting โดยนำ Change Password Component มาใช้
>   - แก้ไขหน้า Admin Dashboard โดยนำ Change Password Component มาใช้
> - แก้ไขหน้า Team Activity
>   - เพิ่มการตรวจสอบและดักต่างๆในการสร้างกิจกรรมของทีม เช่น เวลา ชื่อกิจกรรม รายละเอียดกิจกรรม เป็นต้น
>   - เพิ่มการตรวจสอบและดักต่างๆในการแก้ไขกิจกรรมของทีม เช่น เวลา ชือกิจกรรม รายละเอียดกิจกรรม เป็นต้น
> - แก้ไข Layout ของหน้า Home
> - แก้ไข UI ของหน้า Event บางส่วน
> - แก้ไข UI ของหน้า My Event บางส่วน
> - แก้ไข UI ของหน้า Admin Dashboard บางส่วน