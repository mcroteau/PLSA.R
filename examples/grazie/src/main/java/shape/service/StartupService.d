package shape.service;

import shape.Underscore;
import shape.model.*;
import shape.repo.*;

@Service
public class StartupService {

    @Bind
    TownRepo townRepo;

    @Bind
    RoleRepo roleRepo;

    @Bind
    UserRepo userRepo;

    @Bind
    BusinessRepo businessRepo;

    @Bind
    DbAccess dbAccess;

    @Bind
    SmsService smsService;


    public void init() {

        Parakeet.configure(dbAccess);

        Role superRole = roleRepo.get(Underscore.SUPER_ROLE);
        Role userRole = roleRepo.get(Underscore.USER_ROLE);

        if(superRole == null){
            superRole = new Role();
            superRole.setName(Underscore.SUPER_ROLE);
            roleRepo.save(superRole);
        }

        if(userRole == null){
            userRole = new Role();
            userRole.setName(Underscore.USER_ROLE);
            roleRepo.save(userRole);
        }

        User existing = userRepo.getPhone("9079878652");
        String password = Parakeet.dirty(Underscore.SUPER_PASSWORD);

        superRole = roleRepo.get(Underscore.SUPER_ROLE);

        if(existing == null){
            User superUser = new User();
            superUser.setGuid(Underscore.getString(8).toUpperCase());
            superUser.setUuid(Underscore.getString(8).toUpperCase());
            superUser.setName("Super User!");
            superUser.setPhone("9079878652");
            superUser.setEmail(Underscore.SUPER_USERNAME);
            superUser.setPassword(password);
            userRepo.save(superUser);
            User savedUser = userRepo.getSaved();
            userRepo.saveUserRole(savedUser.getId(), superRole.getId());
            String permission = Underscore.USER_MAINTENANCE + savedUser.getId();
            userRepo.savePermission(savedUser.getId(), permission);
        }

    }

    public void genMocks(){

        String[] towns = new String[]{"Antilles", "Burbin Banks Delaware", "Habiskus"};
        for(String name: towns){
            Town town = new Town();
            town.setName(name);
            townRepo.save(town);

            Town savedTown = townRepo.getSaved();
            savedTown.setLatitude("35.2226");
            savedTown.setLongitude("97.4395");
            townRepo.update(savedTown);
        }

        Business one = new Business();
        one.setName("Software Projects Inc.");
        one.setAddress("Software Projects Inc. Fairbanks Alaska");
        one.setLatitude("45.452191");
        one.setLongitude("-123.9128525");
        one.setTownId(1L);
        businessRepo.save(one);

        Business dos = new Business();
        dos.setName("Petco");
        dos.setAddress("Petco 625 H. Street");
        dos.setLatitude("38.891032");
        dos.setLongitude("-77.168679");
        dos.setTownId(2L);
        businessRepo.save(dos);

        Business tres = new Business();
        tres.setName("Everything But Water");
        tres.setAddress("Everything But Water 4724 1/4 Admiralty Way, Marina Del Rey, CA 90292");
        tres.setLatitude("33.9544111");
        tres.setLongitude("-118.4867234");
        tres.setTownId(1L);
        businessRepo.save(tres);



        String[] names = new String[]{ "Mitch Rithmithgan",
                                        "Cheech Nordom",
                                        "Tito Chavez",
                                        "Lisa Churnem",
                                        "Manuel Smith",
                                        "Allen Aspinwall"};

        for(int z = 0; z < names.length; z++){
            String name = names[z];
            User user = new User();
            user.setGuid(Underscore.getString(8).toUpperCase());
            user.setUuid(Underscore.getString(8).toUpperCase());
            user.setEmail("croteau.mike+" + z + "@gmail.com");
            user.setPassword(Parakeet.dirty("password"));
            user.setTownId(2L);
            userRepo.save(user);

            User savedUser = userRepo.getSaved();
            userRepo.saveUserRole(savedUser.getId(), Underscore.USER_ROLE);
            String permission = Underscore.USER_MAINTENANCE + savedUser.getId();
            userRepo.savePermission(savedUser.getId(), permission);

            savedUser.setImageUri("https://tips.sfo3.digitaloceanspaces.com/tnTPoUwD9.png");
            savedUser.setName(name);
            savedUser.setPhone("9079878652");
            savedUser.setStripeAccountId("acct_1KKrmu2XYbZOgi9P");
            userRepo.update(savedUser);

            UserBusiness userBusiness = new UserBusiness();
            userBusiness.setUserId(savedUser.getId());
            userBusiness.setBusinessId(2L);

            userRepo.saveBusiness(userBusiness);

            UserBusiness savedUserBusiness = userRepo.getSavedBusiness();

            String businessPermission = Underscore.BUSINESS_MAINTENANCE + savedUserBusiness.getId();
            userRepo.savePermission(savedUser.getId(), businessPermission);

        }
    }
}
