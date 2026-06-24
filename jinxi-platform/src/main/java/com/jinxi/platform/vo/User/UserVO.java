package com.jinxi.platform.vo.User;

import com.jinxi.platform.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * 用户信息响应视图对象。
 *
 * <p>不包含密码等敏感字段。</p>
 */
@Data
public class UserVO {

    private Long id;

    private String username;

    private String nickname;

    private String avatar;

    private String email;

    private String phone;



    /**
     * 将用户实体转换为用户响应对象。
     *
     * @param user 用户实体
     * @return 用户响应对象
     */
    public static UserVO from(User user) {
        if (user == null) {
            return null;
        }
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }
}
