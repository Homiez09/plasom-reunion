package cs211.project.controllers.team;

import cs211.project.models.*;
import cs211.project.models.collections.ActivityTeamList;
import cs211.project.models.collections.ChatHistory;
import cs211.project.services.*;
import cs211.project.services.team.LoadSideBarComponent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class TeamChatController {
    @FXML private AnchorPane navbarAnchorPane, sideBarAnchorPane, mainAnchorPane;
    @FXML private TableView groupTableView;
    @FXML private TableColumn<ActivityTeam, String> groupTableColumn;
    @FXML private TextField typeMessageTextField;
    @FXML private Label activityNameLabel;
    @FXML private GridPane chatContainer;
    @FXML private ScrollPane chatScrollPane;
    private final User user = (User) FXRouter.getData();
    private final Event event = (Event) FXRouter.getData2();
    private final Team team = (Team) FXRouter.getData3();
    ActivityTeamListDataSource activityTeamListDataSource = new ActivityTeamListDataSource("data", "team-activity.csv");
    ActivityTeamList activityTeamList = activityTeamListDataSource.readData();
    ChatHistoryDataSource chatHistoryDataSource = new ChatHistoryDataSource("data", "chat-history.csv");
    ChatHistory chatHistory = chatHistoryDataSource.readData();
    User userChatTemp;
    ActivityTeam activitySelectTemp;

    @FXML
    private void initialize(){
        new LoadNavbarComponent(user, navbarAnchorPane);
        new LoadSideBarComponent(sideBarAnchorPane);

        initializeEnterPressForSendMessage();

        checkActivitySelect();
        loadGroupTableView();
    }

    @FXML
    protected void onSendMessageButtonClick() {
        if (activitySelectTemp == null) return;
        if (typeMessageTextField.getText().isEmpty()) return;

        chatHistory.add(new Chat(typeMessageTextField.getText(), user, activitySelectTemp.getActivityID()));
        chatHistoryDataSource.writeData(chatHistory);

        typeMessageTextField.clear();

        loadChatOfActivity(activitySelectTemp);
    }


    private void loadChatOfActivity(ActivityTeam activity) {
        int row = 0;

        chatContainer.getChildren().clear();
        for (Chat chat : chatHistory.getChatByActivityId(activity.getActivityID())) {
            loadMessageBox(chat, row++);
        }

        goToLastMessage();
        checkActivitySelect();
    }

    private void loadMessageBox(Chat chat, int row) {
        Runtime.getRuntime().freeMemory();
        FXMLLoader messageBoxLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/team/chats/message-box.fxml"));
        AnchorPane messageBoxComponent;

        try {
            messageBoxComponent = messageBoxLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MessageBoxController messageBoxController = messageBoxLoader.getController();
        messageBoxController.setChat(chat);

        if (user.getUserId().equals(chat.getSender().getUserId())) {
            messageBoxController.showMessage(2);
        } else if (userChatTemp != null) {
            if (userChatTemp.getUserId().equals(chat.getSender().getUserId())) {
                messageBoxController.showMessage(1);
            } else {
                messageBoxController.showMessage(0);
            }
        } else {
            messageBoxController.showMessage(0);
        }


        userChatTemp = chat.getSender();

        GridPane.setMargin(messageBoxComponent,new Insets(0,0,5,0));
        chatContainer.add(messageBoxComponent, 0, row);
    }

    private void loadGroupTableView(){
        groupTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        groupTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ActivityTeam>() {
            @Override
            public void changed(ObservableValue observableValue, ActivityTeam oldValue, ActivityTeam newValue) {
                if(newValue != null) {
                    activitySelectTemp = newValue;
                    userChatTemp = null;
                    activityTeamList = activityTeamListDataSource.readData();

                    activityNameLabel.setText(newValue.getName());

                    loadChatOfActivity(newValue);
                }
            }
        });

        groupTableView.getColumns().clear();
        groupTableView.getColumns().add(groupTableColumn);

        groupTableView.getItems().clear();
        for(ActivityTeam activityTeam : activityTeamList.getActivitiesByTeamID(team.getTeamID())){
            groupTableView.getItems().add(activityTeam);
        }
    }

    private void checkActivitySelect() {
        if (activitySelectTemp == null) typeMessageTextField.setDisable(true);
        else typeMessageTextField.setDisable(false);
    }

    private void goToLastMessage() {
        chatScrollPane.vvalueProperty().bind(mainAnchorPane.heightProperty());
    }

    
}
