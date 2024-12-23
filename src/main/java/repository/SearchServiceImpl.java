package repository;

import fileService.FileService;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.Contact;
import service.SearchService;

import java.util.List;

public class SearchServiceImpl implements SearchService {

    private final FileService fileService;

    public SearchServiceImpl() {
        fileService = new FileService();


    }

    @Override
    public List<Contact> searchContactByName(String name) {
        List<Contact> contacts = fileService.read();

        return Flowable.fromIterable(contacts)
                .filter(contact -> contact.getName().equalsIgnoreCase(name))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .blockingGet();
    }


    @Override
    public List<Contact> searchContactBySurname(String surname) {
        List<Contact> contacts = fileService.read();

        return Flowable.fromIterable(contacts)
                .filter(contact -> contact.getSurname().equalsIgnoreCase(surname))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .blockingGet();

    }


    @Override
    public List<Contact> searchContactByAddress(String address) {
        List<Contact> contacts = fileService.read();

        return Flowable.fromIterable(contacts)
                .filter(contact -> contact.getAddress().equalsIgnoreCase(address))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .blockingGet();
    }


    @Override
    public List<Contact> searchContactByPhone(String phone) {
        List<Contact> contacts = fileService.read();

        String cleanPhone = phone.replaceAll("\\D", "");
        String formattedPhone = "+996 " + cleanPhone.replaceAll("(.{3})(.{3})(.{3})", "$1 $2 $3");

        return Flowable.fromIterable(contacts)
                .filter(contact -> contact.getPhone().equalsIgnoreCase(formattedPhone))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .blockingGet();

    }


}

