package cs211.project.services;

import cs211.project.models.Activity;
import cs211.project.models.collections.ActivityList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityListDataSourceTest {

    @Test
    @DisplayName("test read data")
    public  void testReadData() {
        ActivityListDataSource dataSource = new ActivityListDataSource("data","activity-list.csv");
        ActivityList activityList = dataSource.readData();

        Activity testActivity = activityList.getActivities().get(0);
        assertEquals("activityName",testActivity.getName());
        assertEquals("20/11/2023 08:00",testActivity.getStartTime());
        assertEquals("Last game of the tournament, Who will be the winner?",activityList.getActivities().get(5).getDescription());
    }
}