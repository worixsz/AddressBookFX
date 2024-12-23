package service;

import model.Contact;

import java.util.List;

public interface UpdateService {

    void update(Contact oldContact, Contact newContact);

}
