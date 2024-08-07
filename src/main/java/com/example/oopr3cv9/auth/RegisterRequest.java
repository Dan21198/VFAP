package com.example.oopr3cv9.auth;

import com.example.oopr3cv9.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String userName;
  private String email;
  private String password;
}
