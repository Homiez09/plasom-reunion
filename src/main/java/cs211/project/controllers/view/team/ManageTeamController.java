package cs211.project.controllers.view.team;

import cs211.project.controllers.component.alertBox.DeleteTeamAlertBoxController;
import cs211.project.controllers.component.sideBarControllers.SideBarTeamController;
import cs211.project.controllers.component.teamControllers.manageTeamController.ManageMemberTeamListController;
import cs211.project.controllers.component.teamControllers.manageTeamController.UnbanUserTeamController;
import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.TeamList;
import cs211.project.models.collections.UserList;
import cs211.project.services.*;
import cs211.project.services.team.LoadSideBarComponent;
import cs211.project.services.team.LoadUserCardProfileComponent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

public class ManageTeamController {
    private final User user = (User) FXRouter.getData();
    private final Event event = (Event) FXRouter.getData2();
    private final Team team = (Team) FXRouter.getData3();

    @FXML private GridPane memberContainer;
    @FXML private AnchorPane navbarAnchorPane, sideBarAnchorPane, membersAnchorPane, settingAnchorPane, settingHeaderAnchorPane, alertBoxAnchorPane, unbanAnchorPane;
    @FXML private Rectangle settingHover, membersHover;
    @FXML private Line memberHoverLine, settingHoverLine;
    @FXML private Label membersLabel, settingLabel;
    @FXML private ImageView nameImageView, roleImageView, statusImageView;
    @FXML private AnchorPane userCardProfileAnchorPane, manageTeamExitAnchorPane, manageTeamDisbleAnchorPane;
    @FXML private ImageView teamNameReqImageView, visiblePasswordImageView, passwordIconView;
    @FXML private Label teamIdLabel, dateRequirementLabel, countDescriptionLabel, nameRequirementLabel, summaryPeriodLabel, passwordReq, errorContinueLabel;
    @FXML private TextField teamNameTextField, showPasswordTextField;
    @FXML private TextArea teamDescriptionTextArea;
    @FXML private PasswordField passwordField;
    @FXML private Spinner<Integer> numMemberSpinner, startHourSpinner, startMinuteSpinner, endHourSpinner, endMinuteSpinner;
    @FXML private ChoiceBox<String> startDateChoiceBox, endDateChoiceBox;
    @FXML private DatePicker startDatePicker, endDatePicker;

    private String description = "" , teamName = "", startDateFormat, endDateFormat, password="", teamID;

    private final String[] time = {"AM", "PM"};
    private String[] startParts, endParts, startDateParts, endDateParts, startTimeParts, endTimeParts;
    private LocalDateTime beforeStartDateTime ;
    private LocalDateTime beforeEndDateTime,afterStartDateTime , afterEndDateTime;
    private LocalDate startDate, endDate;

    private final int MAX_TEAM_NAME_LIMIT = 35, MAX_DESCRIPTION_LIMIT = 280, MAX_PASSWORD_LIMIT = 27;
    private int minimumMember;

    private Image nameIcon, roleIcon, statusIcon, showPasswordImage, hidePasswordImage;
    private boolean teamNameRequirement = true, dateValidateRequirement = true;
    private boolean nameSort, roleSort, statusSort;


    private SpinnerValueFactory<Integer> startHourSpin, endHourSpin;
    private SpinnerValueFactory<Integer> numMemberSpin;


    private final TeamListDataSource teamListDataSource = new TeamListDataSource("data", "team-list.csv");
    private TeamList teamList = teamListDataSource.readData();
    private final JoinTeamMap joinTeamMap = new JoinTeamMap();
    private HashMap<String, UserList> hashMap;

    private UserListDataSource datasource;
    private UserList userList;
    private LoadSideBarComponent sideBarAnchorPaneLoad;


    @FXML
    private void initialize() {
        new LoadNavbarComponent(user, navbarAnchorPane);

        hashMap = joinTeamMap.roleReadData();

        sideBarAnchorPaneLoad = new LoadSideBarComponent();
        sideBarAnchorPane.getChildren().add(sideBarAnchorPaneLoad.getSideBarComponent());

        datasource = new UserListDataSource("data", "user-list.csv");
        userList = datasource.readData();
        loadIcon();
        showUserList(team.getTeamID());
        setManageTeamDisableAnchorPane(false);
        setSideBar();


        if(!team.getTeamHostUser().equals(user)){
            settingHeaderAnchorPane.setVisible(false);
            visiblePasswordImageView.setVisible(false);
        }else{
            loadTeamDataInit();
            visiblePasswordImageView.setVisible(false);
            passwordIconView.setVisible(false);

            maximumLengthField();
            choiceBoxInit();

            validateDateTime();
            validatePeriod();
            checkDateReq();

            eventHandleEnter();

        }
    }

    @FXML
    private void onBannedButtonClick() throws IOException {
        FXMLLoader unbanLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/team/manage-team/unban-user-team.fxml"));
        unbanAnchorPane.getChildren().add(unbanLoader.load());
        UnbanUserTeamController unbanUserTeamController = unbanLoader.getController();
        unbanUserTeamController.setup(team);
        //blur
        manageTeamDisbleAnchorPane.setEffect(new BoxBlur(6, 5, 2));
        manageTeamDisbleAnchorPane.setDisable(true);
        unbanAnchorPane.setVisible(true);
    }

    private void eventHandleEnter(){
        EventHandler<KeyEvent> enterEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    onSaveButtonClick();
                }
            }
        };
        teamNameTextField.setOnKeyPressed(enterEventHandler);
        teamDescriptionTextArea.setOnKeyPressed(enterEventHandler);
        passwordField.setOnKeyPressed(enterEventHandler);
        showPasswordTextField.setOnKeyPressed(enterEventHandler);
    }

    public void loadAlertBox(Team team) {
        FXMLLoader alertBoxLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/alert-box/delete-team-alert-box.fxml"));
        AnchorPane alertBoxComponent;
        try {
            alertBoxComponent = alertBoxLoader.load();
            DeleteTeamAlertBoxController deleteTeamAlertBoxController = alertBoxLoader.getController();
            deleteTeamAlertBoxController.setTeam(team);
            deleteTeamAlertBoxController.setManageTeamController(this);

            alertBoxAnchorPane.getChildren().add(alertBoxComponent);

            manageTeamDisbleAnchorPane.setEffect(new BoxBlur(6, 5, 2));
            manageTeamDisbleAnchorPane.setDisable(true);
            manageTeamExitAnchorPane.setVisible(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        alertBoxAnchorPane.setVisible(true);
    }

    public void reloadDataHashMap() {
        hashMap = joinTeamMap.roleReadData();
    }

    public void showUserList(String teamID) {
        memberContainer.getChildren().clear();
        if (hashMap.isEmpty()) return;
        UserList memberList = hashMap.get(teamID);
        for (User user : memberList.getUsers()) {
            loadManageTeamComponent(user, 0, memberList.getUsers().indexOf(user), teamID);
        }
        this.teamID = teamID;

    }

    private void isMemberAnchorPaneVisible(boolean isVisible) {
        if (isVisible) {
            membersAnchorPane.setVisible(true);
            settingAnchorPane.setVisible(false);
        } else {
            membersAnchorPane.setVisible(false);
            settingAnchorPane.setVisible(true);
        }
    }
    private void setSideBar() {
        SideBarTeamController sideBarTeamController = sideBarAnchorPaneLoad.getController();
        sideBarTeamController.setHoverManageTeam();
    }

    private void setSettingHover(boolean isHover) {
        if (isHover) {
            settingLabel.setStyle("-fx-text-fill: #6B6B6B");
        } else {
            settingLabel.setStyle("");
        }
        settingHover.setVisible(isHover);
        settingHoverLine.setVisible(isHover);
    }

    private void setMemberClickPage(boolean isMemberClick) {
        if (isMemberClick) {
            memberHoverLine.setVisible(true);
            membersLabel.setStyle("");
            membersHover.setVisible(false);
        } else {
            settingHoverLine.setVisible(true);
            settingLabel.setStyle("");
            settingHover.setVisible(false);
        }
    }

    private void setMembersHover(boolean isHover) {
        if (isHover) {
            membersLabel.setStyle("-fx-text-fill: #6B6B6B");
        } else {
            membersLabel.setStyle("");
        }
        membersHover.setVisible(isHover);
        memberHoverLine.setVisible(isHover);
    }


    private void setManageTeamDisableAnchorPane(boolean isDisable) {
        manageTeamDisbleAnchorPane.setDisable(isDisable);
        manageTeamExitAnchorPane.setVisible(isDisable);
        if (isDisable) {
            manageTeamDisbleAnchorPane.setEffect(new BoxBlur(6, 5, 2));
        } else {
            manageTeamDisbleAnchorPane.setEffect(null);
        }
    }


    private void loadManageTeamComponent(User user, int col, int row, String teamID) {
        Team team = teamList.findTeamByID(teamID);
        FXMLLoader manageTeamLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/team/manage-team/manage-member-team-list-1.fxml"));
        AnchorPane manageTeamComponent;
        if (user != null) {
            try {
                manageTeamComponent = manageTeamLoader.load();
                Label nameLabel = (Label) manageTeamComponent.getChildren().get(0);
                AnchorPane roleAnchorPane = (AnchorPane) manageTeamComponent.getChildren().get(1);
                ImageView roleImageView = (ImageView) roleAnchorPane.getChildren().get(0);
                Label roleLabel = (Label) roleAnchorPane.getChildren().get(1);
                ImageView menuImageView = (ImageView) manageTeamComponent.getChildren().get(2);
                Label idLabel = (Label) manageTeamComponent.getChildren().get(4);
                Label statusLabel = (Label) manageTeamComponent.getChildren().get(5);
                ImageView statusImageView = (ImageView) manageTeamComponent.getChildren().get(6);
                ComboBox menuComboBox = (ComboBox) manageTeamComponent.getChildren().get(9);

                nameLabel.setText(user.getUsername());
                roleLabel.setText(user.getRole());
                idLabel.setText(user.getUsername());
                statusLabel.setText((user.getStatus()) ? "online" : "offline");

                roleImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/team-box/role/" + user.getRole() + ".png")));
                menuImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/team-box/dot_icon.png")));
                statusImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/login/status/" + statusLabel.getText() + "_active.png")));

                String role = (user.getRole().equals("Leader") ? "Member" : "Leader");
                String[] menuOwnerItems = {"View Profile", "Kick", "Ban", "Promote to " + role};
                String[] menuLeaderItems = {"View Profile", "Kick", "Ban"};
                String[] menuMemberItems = {"View Profile"};
                String[] menuItems;

                User userTemp = team.getMemberList().findUserId(this.user.getUserId());
                if (userTemp.getRole().equals("Owner")) {
                    menuItems = (!userTemp.getUserId().equals(user.getUserId()) ? menuOwnerItems : menuMemberItems);
                } else if (userTemp.getRole().equals("Leader") && !user.getRole().equals("Owner")) {
                    menuItems = (!userTemp.getUserId().equals(user.getUserId()) ? menuLeaderItems : menuMemberItems);
                } else {
                    menuItems = menuMemberItems;
                }

                menuComboBox.getItems().addAll(menuItems);
                menuComboBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
                    if (newValue == null) return;

                    ManageMemberTeamListController manageMemberTeamListController = manageTeamLoader.getController();
                    manageMemberTeamListController.setManageTeamController(this);

                    if (newValue.equals("Promote to Leader")) {
                        try {
                            manageMemberTeamListController.goTo((String) newValue, teamID, user.getUserId());
                            showUserList(teamID);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (newValue.equals("Promote to Member")) {
                        try {
                            manageMemberTeamListController.goTo((String) newValue, teamID, user.getUserId());
                            showUserList(teamID);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (newValue.equals("Kick") || newValue.equals("Leave Team")) {
                        try {
                            manageMemberTeamListController.goTo((String) newValue, teamID);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (newValue.equals("Ban")) {
                        try {
                            manageMemberTeamListController.goTo((String) newValue, teamID, user.getUserId());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (newValue.equals("View Profile")) {

                        setManageTeamDisableAnchorPane(true);
                        new LoadUserCardProfileComponent(userCardProfileAnchorPane, user);
                        userCardProfileAnchorPane.setVisible(true);
                    }

                    showUserList(teamID);
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return;
        }
        GridPane.setMargin(manageTeamComponent, new Insets(0, 0, 5, 0));
        memberContainer.add(manageTeamComponent, col, row);
    }

    @FXML private void onSortNameClick() {
        if (hashMap.isEmpty()) return;
        UserList memberList = hashMap.get(teamID);
        memberList.sort(new UsernameUserComparator());
        if (nameSort) {
            memberList.reverse();
        }
        for (User user : memberList.getUsers()) {
            loadManageTeamComponent(user, 0, memberList.getUsers().indexOf(user), teamID);
        }
        nameSort = !nameSort;
    }
    @FXML private void onSortRoleClick() {
        if (hashMap.isEmpty()) return;
        UserList memberList = hashMap.get(teamID);
        memberList.sort(new RoleUserComparator());
        if (roleSort) {
            memberList.reverse();
        }
        for (User user : memberList.getUsers()) {
            loadManageTeamComponent(user, 0, memberList.getUsers().indexOf(user), teamID);
        }
        roleSort = !roleSort;
    }
    @FXML private void onSortStatusClick() {
        if (hashMap.isEmpty()) return;
        UserList memberList = hashMap.get(teamID);
        memberList.sort(new StatusUserComparator());
        if (statusSort) {
            memberList.reverse();
        }
        for (User user : memberList.getUsers()) {
            loadManageTeamComponent(user, 0, memberList.getUsers().indexOf(user), teamID);
        }
        statusSort = !statusSort;
    }
    @FXML private void onManageTeamAnchorPaneClick() {
        setManageTeamDisableAnchorPane(false);
        userCardProfileAnchorPane.setVisible(false);
    }
    @FXML private void onMembersClick() {
        setMemberClickPage(true);
        settingHoverLine.setVisible(false);
        isMemberAnchorPaneVisible(true);
        loadTeamDataInit();
        visiblePasswordImageView.setVisible(false);
        passwordIconView.setVisible(false);
    }
    @FXML private void onSettingClick() {
        setMemberClickPage(false);
        memberHoverLine.setVisible(false);
        isMemberAnchorPaneVisible(false);
        loadTeamDataInit();
    }
    @FXML private void onDeleteButtonClick(){
        if(user.validatePassword(password)){
            loadAlertBox(team);
        }else if(!user.validatePassword(password)){
            passwordReq.setVisible(true);
            passwordReq.setStyle("-fx-text-fill: #EA5845");
            passwordReq.setText("Wrong password. Please Try Again.");
        }else{
            passwordReq.setVisible(false);
        }
    }

    @FXML private void onSaveButtonClick(){
        teamName = teamNameTextField.getText();
        description = teamDescriptionTextArea.getText();

        if(user.validatePassword(password) && dateValidateRequirement &&  teamNameRequirement){
            TeamList teamList = teamListDataSource.readData();
            teamList.updateTeam(team.getTeamID(), teamName, numMemberSpinner.getValue(), startDateFormat, endDateFormat, description);
            teamListDataSource.writeData(teamList);
            Team teamTemp = teamList.findTeamByID(team.getTeamID());
            try {
                FXRouter.goTo("team-manage",user, event, teamTemp);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            if(teamName == null){
                errorContinueLabel.setText("Please make sure to fill in all the required information.");
            }else if(password == null){
                passwordReq.setVisible(true);
                passwordReq.setStyle("-fx-text-fill: #EA5845");
                passwordReq.setText("Enter your password to save changes to your profile.");
            }else if(!user.validatePassword(password)){
                passwordReq.setVisible(true);
                passwordReq.setStyle("-fx-text-fill: #EA5845");
                passwordReq.setText("Wrong password. Please Try Again.");
            }else{
                passwordReq.setVisible(false);
            }
        }
    }

    @FXML private void onVisiblePasswordClick() {
        if (visiblePasswordImageView.getImage() == hidePasswordImage) {
            passwordField.setVisible(false);
            showPasswordTextField.setVisible(true);
            visiblePasswordImageView.setImage(showPasswordImage);
        } else {
            passwordField.setVisible(true);
            showPasswordTextField.setVisible(false);
            visiblePasswordImageView.setImage(hidePasswordImage);
        }
    }
    @FXML private void onMembersEntered() {
        setMembersHover(true);
        if (!settingAnchorPane.isVisible()) {
            setSettingHover(false);
        }
        if (!membersAnchorPane.isVisible()) {
            setMembersHover(true);
        }
    }
    @FXML private void onMembersExited() {
        if (!membersAnchorPane.isVisible()) {
            setMembersHover(false);
        } else {
            membersHover.setVisible(false);
            membersLabel.setStyle("");
        }
    }
    @FXML private void onSettingEntered() {
        setSettingHover(true);
        if (!membersAnchorPane.isVisible()) {
            setMembersHover(false);
        }
        if (!settingAnchorPane.isVisible()) {
            setSettingHover(true);
        }
    }
    @FXML private void onSettingExited() {
        if (!settingAnchorPane.isVisible()) {
            setSettingHover(false);
        } else {
            settingHover.setVisible(false);
            settingLabel.setStyle("");
        }
    }


    private void setDatePicker(){
        int startYear, startMonth, startDay, startHour, startMinute;
        int endYear, endMonth, endDay, endHour, endMinute;
        startParts = team.getStartDateTime().split(" | ");
        startDateParts = startParts[0].split("/");
        startTimeParts = startParts[2].split(":");

        endParts = team.getEndDateTime().split(" | ");
        endDateParts = endParts[0].split("/");
        endTimeParts = endParts[2].split(":");

        startYear = Integer.parseInt(startDateParts[2]);
        startMonth = Integer.parseInt(startDateParts[1]);
        startDay = Integer.parseInt(startDateParts[0]);
        startHour =  Integer.parseInt(startTimeParts[0]);
        startMinute =  Integer.parseInt(startTimeParts[1]);

        endYear = Integer.parseInt(endDateParts[2]);
        endMonth = Integer.parseInt(endDateParts[1]);
        endDay = Integer.parseInt(endDateParts[0]);
        endHour =  Integer.parseInt(endTimeParts[0]);
        endMinute =  Integer.parseInt(endTimeParts[1]);

        startDate = LocalDate.of(startYear, startMonth, startDay);
        endDate = LocalDate.of(endYear, endMonth, endDay);

        startDatePicker.setValue(startDate);
        endDatePicker.setValue(endDate);

        startDateChoiceBox.setValue(startParts[3]);
        endDateChoiceBox.setValue(endParts[3]);

        if (startDateChoiceBox.getValue().equals("PM")) {
            startHourSpin = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12,startHour);
        } else {
            startHourSpin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 11,startHour);
        }
        startHourSpinner.setValueFactory(startHourSpin);

        if (endDateChoiceBox.getValue().equals("PM")) {
            endHourSpin = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12,endHour);
        } else {
            endHourSpin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 11,endHour);
        }
        endHourSpinner.setValueFactory(endHourSpin);

        startMinuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,startMinute));
        endMinuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59, endMinute));

        if (startParts[3].equals("PM")) {
            if (startHour != 12) {
                startHour += 12;
            }
        } else if (startHour == 12) {
            startHour = 0;
        }

        if (endParts[3].equals("PM")) {
            if (endHour != 12) {
                endHour += 12;
            }
        } else if (endHour == 12) {
            endHour = 0;
        }

        beforeStartDateTime = LocalDateTime.of(startYear, startMonth, startDay, startHour, startMinute);
        beforeEndDateTime = LocalDateTime.of(endYear, endMonth, startDay, endHour, endMinute);

    }

    @FXML private void onKeyHidePassword() {
        passwordReq.setVisible(false);
        password = passwordField.getText();
        showPasswordTextField.setText(password);
    }

    @FXML private void onKeyShowPassword() {
        passwordReq.setVisible(false);
        password = showPasswordTextField.getText();
        passwordField.setText(password);
    }

    @FXML private void onKeyTeamName(){
        checkTeamNameReq();
    }

    @FXML private void onKeyDescription(){
        description = teamDescriptionTextArea.getText();
        countDescriptionLabel.setText(String.valueOf((int) description.length()));
        if (description.length() >= 280) {
            if (description.length() > 280) {
                countDescriptionLabel.setStyle("-fx-text-fill: #EA5845 ");
            } else {
                countDescriptionLabel.setStyle("");
            }
        }else {
            countDescriptionLabel.setStyle("");
        }
    }

    private void maximumLengthField(){
        teamNameTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.length() > MAX_TEAM_NAME_LIMIT) {
                teamNameTextField.setText(oldValue);
            }
        });
        teamDescriptionTextArea.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.length() > MAX_DESCRIPTION_LIMIT) {
                teamDescriptionTextArea.setText(oldValue);
            }
            teamDescriptionTextArea.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    teamDescriptionTextArea.setText(oldValue);
                }
            });
        });
        passwordField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.length() > MAX_PASSWORD_LIMIT) {
                passwordField.setText(oldValue);
            }
        });

        showPasswordTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.length() > MAX_PASSWORD_LIMIT) {
                showPasswordTextField.setText(oldValue);
            }
        });
    }

    private void loadTeamDataInit(){
        teamIdLabel.setText(team.getTeamID());
        teamNameTextField.setText(team.getTeamName());
        teamDescriptionTextArea.setText(team.getTeamDescription());
        countDescriptionLabel.setText(String.valueOf(team.getTeamDescription().length()));
        passwordField.setText("");
        showPasswordTextField.setText("");

        dateRequirementLabel.setVisible(false);
        nameRequirementLabel.setVisible(false);
        passwordReq.setVisible(false);
        errorContinueLabel.setVisible(false);
        visiblePasswordImageView.setVisible(true);
        passwordIconView.setVisible(true);
        showPasswordTextField.setVisible(false);
        passwordField.setVisible(true);

        setDatePicker();
        numMemberReq();
    }

    private void choiceBoxInit() {
        startDateChoiceBox.getItems().addAll(time);
        endDateChoiceBox.getItems().addAll(time);

        startDatePicker.getEditor().setDisable(true);
        endDatePicker.getEditor().setDisable(true);
    }

    private void checkTeamNameReq(){
        teamNameRequirement = false;
        teamName = teamNameTextField.getText();
        teamNameRequirement = (!teamName.isEmpty() && teamList.findTeamByNameInEvent(teamName, event.getEventID()) == null) || team.getTeamName().equals(teamName);
        if(teamName.isEmpty()){
            teamNameReqImageView.setVisible(false);
            nameRequirementLabel.setVisible(true);
            nameRequirementLabel.setText("Incorrect Team name.");
        }else if(teamList.findTeamByNameInEvent(teamName, event.getEventID()) != null  && !team.getTeamName().equals(teamName)){
            teamNameReqImageView.setVisible(false);
            nameRequirementLabel.setVisible(true);
            nameRequirementLabel.setText("Duplicate Team name. Please use another name.");
        }else{
            teamNameReqImageView.setVisible(true);
            nameRequirementLabel.setVisible(false);
        }
    }

    private void checkDateReq(){
        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> validatePeriod());
        endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> validatePeriod());
        startHourSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validatePeriod());
        startMinuteSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validatePeriod());
        endHourSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validatePeriod());
        endMinuteSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validatePeriod());
        startDateChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> validatePeriod());
        endDateChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> validatePeriod());
    }

    private void numMemberReq(){
        minimumMember = team.getMemberList().getUsers().size();
        if(minimumMember == 1) minimumMember = 2;
        numMemberSpin =new SpinnerValueFactory.IntegerSpinnerValueFactory(minimumMember, 1000000000, team.getMaxSlotTeamMember());
        numMemberSpinner.setValueFactory(numMemberSpin);
    }

    private void loadIcon() {
        nameIcon = new Image(getClass().getResourceAsStream("/images/icons/team/sortIcon.png"));
        roleIcon = new Image(getClass().getResourceAsStream("/images/icons/team/sortIcon.png"));
        statusIcon = new Image(getClass().getResourceAsStream("/images/icons/team/sortIcon.png"));
        nameImageView.setImage(nameIcon);
        roleImageView.setImage(roleIcon);
        statusImageView.setImage(statusIcon);

        showPasswordImage = new Image(getClass().getResourceAsStream("/images/icons/login/show_password.png"));
        hidePasswordImage = new Image(getClass().getResourceAsStream("/images/icons/login/hide_password.png"));

        isMemberAnchorPaneVisible(true);
        passwordIconView.setImage(new Image(getClass().getResourceAsStream("/images/icons/login/password_field.png")));

        visiblePasswordImageView.setImage(hidePasswordImage);
    }

    public void closeAlertBox() {
        alertBoxAnchorPane.setVisible(false);
        manageTeamDisbleAnchorPane.setEffect(null);
        manageTeamDisbleAnchorPane.setDisable(false);
    }

    private void validatePeriod(){
        int startHour, startMinute;
        int endHour, endMinute;
        String startTime, endTime;

        startDate = startDatePicker.getValue();
        endDate = endDatePicker.getValue();

        startHour = startHourSpin.getValue();
        endHour = endHourSpin.getValue();

        startMinute = startMinuteSpinner.getValue();
        endMinute = endMinuteSpinner.getValue();

        startTime = startDateChoiceBox.getValue();
        endTime = endDateChoiceBox.getValue();

        if (startTime.equals("PM")) {
            if (startHour != 12) {
                startHour += 12;
            }
        } else if (startHour == 12) {
            startHour = 0;
        }

        if (endTime.equals("PM")) {
            if (endHour != 12) {
                endHour += 12;
            }
        } else if (endHour == 12) {
            endHour = 0;
        }

        afterStartDateTime = LocalDateTime.of(startDate, LocalTime.of(startHour, startMinute));
        afterEndDateTime = LocalDateTime.of(endDate, LocalTime.of(endHour, endMinute));

        startDateFormat = afterStartDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm").withLocale(Locale.US));
        endDateFormat = afterEndDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm").withLocale(Locale.US));

        if(afterStartDateTime.isAfter(beforeStartDateTime) && afterStartDateTime.isBefore(afterEndDateTime) || afterStartDateTime.isEqual(beforeStartDateTime) && afterStartDateTime.isBefore(afterEndDateTime)){
            dateValidateRequirement = true;
            dateRequirementLabel.setVisible(false);
            summaryPeriodLabel.setVisible(true);
            DateTimeFormatter dateFormatted = DateTimeFormatter.ofPattern("MMMM, dd, yyyy 'from' hh:mm a");

            String formattedStartDate = afterStartDateTime.format(dateFormatted.withLocale(Locale.ENGLISH));
            String formattedEndDate = afterEndDateTime.format(dateFormatted.withLocale(Locale.ENGLISH));

            summaryPeriodLabel.setText("This team will take place on " + formattedStartDate + " until " + formattedEndDate);
        }else{
            dateRequirementLabel.setVisible(true);
            dateRequirementLabel.setText("Please specify the correct Team period.");
            dateValidateRequirement = false;
            summaryPeriodLabel.setVisible(false);
        }
    }

    private void validateDateTime() {
        startDateChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (startDateChoiceBox.getValue().equals("PM")) {
                startHourSpin = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12);
            } else {
                startHourSpin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 11);
            }
            startHourSpinner.setValueFactory(startHourSpin);
        });

        endDateChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (endDateChoiceBox.getValue().equals("PM")) {
                endHourSpin = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12);
            } else {
                endHourSpin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 11);
            }
            endHourSpinner.setValueFactory(endHourSpin);
        });

        SpinnerValueFactory<Integer> minute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        SpinnerValueFactory<Integer> endMinute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);

        startMinuteSpinner.setValueFactory(minute);
        endMinuteSpinner.setValueFactory(endMinute);
    }

}
