package service;

import model.Contact;


public interface UpdateService {

    void update(Contact oldContact, Contact newContact);

}
