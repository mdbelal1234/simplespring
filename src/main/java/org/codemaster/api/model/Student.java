package org.codemaster.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
   private Integer id;

   private String firstName;

   private String LastName;

   private String email;
}
