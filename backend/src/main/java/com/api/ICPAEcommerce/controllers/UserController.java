package com.api.ICPAEcommerce.controllers;

import com.api.ICPAEcommerce.domain.user.UserRegisterDTO;
import com.api.ICPAEcommerce.domain.user.UserUpdateDTO;
import com.api.ICPAEcommerce.repositories.UserRepository;
import com.api.ICPAEcommerce.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Rest Controller para requisições de usuário
 * @author Adrian Gabriel K. dos Santos
 */

@RestController
@RequestMapping("/api/v2/users")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    /** End-point de criação de usuários
     * @return DTO Usuário - informações para salvar usuário no BD
     */
   @PostMapping("/register")
   public ResponseEntity registerUser(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {

       return userService.saveUser(userRegisterDTO);
   }

    /** End-point para listagem de usuários
     *
     * @return Se há usuário - List de usuários existentes no BD
     *         Se não há - mensagem para usuário
     */
    @GetMapping("/list-users")
    @ResponseBody
    public ResponseEntity listUsers() {
        var users = userRepository.findAll();
        if(!users.isEmpty()) {
            return ResponseEntity.ok(users);
        }

        return ResponseEntity.ok("Não há usuários cadastrados");
    }

    /** End-point para listar usuário pelo ID
     *
     * @return DTO Usuário -  usuário com o Id escolhido
     */
    @GetMapping("/list-user/{userId}")
    @ResponseBody
    public ResponseEntity listUserById(@PathVariable Long userId) {
        var user = userRepository.getReferenceById(userId);

        return ResponseEntity.ok(user);
    }

    /** End-point para atualizar informações do Usuário
     *
     * @return DTO Usuário - usuário com infos atualizadas
     */
    @PutMapping("/update-user/{userId}")
    public ResponseEntity updateUser(@PathVariable Long userId, @RequestBody UserUpdateDTO userUpdateDTO) {
        var user = userService.updateUser(userId, userUpdateDTO);

        return ResponseEntity.ok(user);
    }

    /** End-point para deletar usuário
     *
     * @return 200 - mensagem de confirmação
     */
    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);

        return ResponseEntity.ok("Usuário excluído com sucesso!");
    }

}
