package com.learn.vo.system;

import lombok.Data;

/*登录请求后 返回的数据，包含 token 和 refresh_token*/
@Data
public class LoginVo {
  private String token;
  private String refresh_token;
}
