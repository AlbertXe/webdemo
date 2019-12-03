package com.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author AlbertXe
 * @date 2019-12-03 14:29
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(DateUtils.class)
public class UserServiceTest {

    @Test
    public void test2(){
        PowerMockito.mockStatic(DateUtils.class);
        PowerMockito.when(DateUtils.getTimeNow()).thenReturn("120003");
        System.out.println(DateUtils.getTimeNow());
    }

}
