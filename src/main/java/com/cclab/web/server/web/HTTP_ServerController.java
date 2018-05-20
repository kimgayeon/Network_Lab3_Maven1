package com.cclab.web.server.web;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cclab.web.server.domain.Member;
import com.cclab.web.server.repository.MemberRepository;
import com.cclab.web.server.domain.Login;
import com.cclab.web.server.repository.LoginRepository;

@Controller
public class HTTP_ServerController {

    @Autowired
    MemberRepository repo;
    
    @Autowired
    LoginRepository repo2;

    @RequestMapping("/")
    public String index(Model model) {
        return "index.html";
    }
    
    
    @RequestMapping("/hong.html")
    public String index4(Model model) {
        return "redirect://www.naver.com/index.html";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/member", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody List<Member> getMember() {
    	System.out.println("Member Called");
        return repo.findAll();
    }

    // --------------------------------------
    // (3-1) <개별고객(검색)> 요청메시지 처리메소드 추가 위치
    // --------------------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/member/{id}", produces = {MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseEntity<Member> getMemberById(@PathVariable Long id){
       Optional<Member> m  = repo.findById(id);
       if(m == null) {
          return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
       }
       Member m2 = m.get();
       System.out.println("Member called seperately");
       
       return new ResponseEntity<Member>(m2, HttpStatus.OK);
    }




    // --------------------------------------
    // (3-2) <신규고객(추가)> 요청메시지 처리메소드 추가 위치
    // --------------------------------------
//    @RequestMapping(method = RequestMethod.POST, value = "/member", produces = {MediaType.APPLICATION_JSON_VALUE })
//    public @ResponseBody ResponseEntity<Member> getMember(@PathVariable final Member member) {
//       char ch = ' ';
//       System.out.println("hihi");
//       ResponseEntity<Member> r = new ResponseEntity<Member>(member, HttpStatus.OK);
//       Member m = new Member();
//       if(member.getRrid().length()>=8) ch = member.getRrid().charAt(7);
//       if(member.getName().isEmpty() || member.getWeight() == 0 ||
//          member.getRrid().isEmpty() || member.getRrid().length() < 8 || ch < '1' || ch > '4') {
//          if(member.getName().isEmpty()) m.setName("#error");
//          if(member.getWeight() == 0) m.setWeight(-1);
//          if(member.getRrid().isEmpty() || member.getRrid().length() < 8 || ch < '1' || ch > '4') m.setRrid("#error");
//          return new ResponseEntity<Member>(m, HttpStatus.NOT_ACCEPTABLE);
//       }
//       m.setName(member.getName());
//       m.setWeight(member.getWeight());
//       m.setRrid(member.getRrid());
//       m.setGender((ch == '1' || ch == '3') ? "남자" : "여자");
//       Member m2 = repo.save(m);
//       return new ResponseEntity<Member>(m2, HttpStatus.OK);
//       
//       
//    }


   @RequestMapping(value = "/member", method = RequestMethod.POST)
   public ResponseEntity<Member> createMember(@RequestBody Member member) {
      char ch = ' ';
      
      Member m = new Member();
       if(member.getRrid().length()>=8) ch = member.getRrid().charAt(7);
       
       if(member.getName().isEmpty() || member.getWeight() == 0 ||
          member.getRrid().isEmpty() || member.getRrid().length() < 8 || ch < '1' || ch > '4') {
          if(member.getName().isEmpty()) m.setName("#error");
          if(member.getWeight() == 0) m.setWeight(-1);
          if(member.getRrid().isEmpty() || member.getRrid().length() < 8 || ch < '1' || ch > '4') m.setRrid("#error");
          return new ResponseEntity<Member>(m, HttpStatus.NOT_ACCEPTABLE);
       }
       m.setName(member.getName());
       m.setWeight(member.getWeight());
       m.setRrid(member.getRrid());
       m.setGender((ch == '1' || ch == '3') ? "남자" : "여자");
       Member m2 = repo.save(m);
      return new ResponseEntity<Member>(m2,HttpStatus.OK);
   }
    



    @RequestMapping(value = "/member/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Member> update(@PathVariable Long id, @RequestBody Member member) { 
       char ch = ' ';
       Optional<Member> m = repo.findById(id);
        if (m == null) {
            return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
        }

       Member m2 = m.get();
       if(member.getName()!="") m2.setName(member.getName());
       if(member.getWeight()!=0) m2.setWeight(member.getWeight());
       if(member.getRrid()!="") {
          m2.setRrid(member.getRrid());
          if(member.getRrid().length()>=8) ch = member.getRrid().charAt(7);
          m2.setGender((ch == '1' || ch == '3')  ? "남자" : "여자");
       }

        repo.save(m2);
        return new ResponseEntity<Member>(m2, HttpStatus.OK);
    }

    // --------------------------------------
    // (3-3) <고객탈퇴(삭제)> 요청메시지 처리메소드 추가 위치
    // --------------------------------------

     @RequestMapping(method = RequestMethod.DELETE, value ="/member/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
     public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        Optional<Member> m = repo.findById(id);
        
        if(m == null) {
           return new ResponseEntity<Void> (HttpStatus.NOT_FOUND);
        }
        
        repo.deleteById(id);
        
        return new ResponseEntity<Void>(HttpStatus.OK);
        
     }
     
     
     //////////////////////////////////////////////////////////////
     
     @RequestMapping(method = RequestMethod.GET, value = "/login", produces = { MediaType.APPLICATION_JSON_VALUE })
     public @ResponseBody List<Login> getLogin() {
         return repo2.findAll();
     }
     
     @RequestMapping(value = "/Login/{account}", method = RequestMethod.PUT)
     public ResponseEntity<Login> update(@PathVariable String account, @RequestBody Login login){ // @ : springboot가 가지고 있는 전단계 처리
        Optional<Login> l = repo2.findById(account);
         if (l == null) {
             return new ResponseEntity<Login>(HttpStatus.NOT_FOUND);
         }

        Login l2 = l.get();
        if(login.getAccount()!="") l2.setAccount(login.getAccount());
        if(login.getPassword()!="") l2.setPassword(login.getPassword());
        if(login.getEmail()!="") l2.setEmail(login.getEmail());
        if(login.getLevel()!=0) l2.setLevel(login.getLevel());
        
         repo2.save(l2);
         return new ResponseEntity<Login>(l2, HttpStatus.OK);
     }


}