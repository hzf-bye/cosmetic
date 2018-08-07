package service;

import base.BaseTest;
import com.cos.common.entity.BizResult;
import com.cos.portal.service.intf.MeetingPlaceService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @description: 会场service测试类
 * @author: hzf
 * @date: 2018/2/7 下午2:27
 */
public class MeetingPlaceServiceTest extends BaseTest {

    @Autowired
    MeetingPlaceService meetingPlaceService;

    @Test
    public void selectMeetingPlaceTest(){
        BizResult result = meetingPlaceService.selectMeetingPlace(1, 2);
        System.out.println();
    }


}
