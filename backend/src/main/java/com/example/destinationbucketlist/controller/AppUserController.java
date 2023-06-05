package com.example.destinationbucketlist.controller;

//@RestController
//public class AppUserController {
//
//    @Autowired
//    private AppUserService appUserService;
//
//    @Autowired
//    private DestinationService destinationService;
//
//    @Autowired
//    private Mapper mapper;
//
//    // localhost:8080/appusers
//    @GetMapping("/appusers")
//    List<AppUserDTO> getAllUsers() {
//        return appUserService.getAllUsers()
//                .stream()
//                .map(mapper::toAppUserDTO)
//                .collect(Collectors.toList());
//    }
//
//    // localhost:8080/appusers/{id}
//    @GetMapping("/appusers/{userId}")
//    Optional<AppUserDTO> getUserById(@PathVariable Integer userId) {
//        return appUserService.getUserById(userId)
//                .stream()
//                .map(mapper::toAppUserDTO)
//                .findFirst();
//    }
//
//    // localhost:8080/appusers
//    // the JSON body
//    // {
//    //    "firstName": "fname",
//    //    "lastName": "lname",
//    //    "username": "username",
//    //    "email": "user@mail.com",
//    //    "password": "password",
//    //    "privateDestinations": []
//    //}
//    @PostMapping("/appusers")
//    void addUser(@RequestBody AppUser appUser) {
//        this.appUserService.addUser(appUser);
//    }
//
//    // localhost:8080/appusers/{id}
//    // the JSON body
//    // {
//    //    "firstName": "fname",
//    //    "lastName": "lname",
//    //    "username": "username",
//    //    "email": "user@mail.com",
//    //    "password": "password",
//    //    "privateDestinations": []
//    //}
//    @PutMapping("/appusers/{userId}")
//    void updateUser(@RequestBody AppUser appUser, @PathVariable Integer userId) {
//        appUser.setId(userId);
//        appUserService.updateUser(appUser);
//    }
//
//    // localhost:8080/appusers/{id}
//    @DeleteMapping("/appusers/{userId}")
//    void deleteUser(@PathVariable Integer userId) {
//        appUserService.deleteUser(userId);
//    }
//
//    // localhost:8080/appusers/{id}/destination
//    // the JSON body
//    // {
//    //    "appUser": { "id": 152 },
//    //    "isFavourite": "false",
//    //    "geolocation": "geo2",
//    //    "title": "Paris",
//    //    "image": "image Paris",
//    //    "description": "windy",
//    //    "startDate": "2023-05-19",
//    //    "endDate": "2023-05-24"
//    //}
////    @PostMapping("/appusers/{userId}/destination")
////    void addPrivateDestToUser(@PathVariable Integer userId, @RequestBody Destination destination) {
////        AppUser appUser = appUserService.getUserById(userId).orElse(null);
////        destination.setAppUser(appUser);
////        destinationService.addDestination(destination);
////        appUser.getDestinations().add(destination);
////        appUserService.addUser(appUser);
////    }
//}
