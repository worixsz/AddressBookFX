package repository;

import fileService.FileService;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.Contact;
import service.SearchService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchServiceImpl implements SearchService {

    private final FileService fileService;

    public SearchServiceImpl() {
        fileService = new FileService();


    }

    @Override
    public List<Contact> searchContactByName(String name) {
        List<Contact> contacts = fileService.read();
        return contacts.stream()
                .filter(contact -> contact.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }


    @Override
    public List<Contact> searchContactBySurname(String surname) {
        List<Contact> contacts = fileService.read();
        return contacts.stream()
                .filter(contact -> contact.getSurname().equalsIgnoreCase(surname))
                .collect(Collectors.toList());
    }


    @Override
    public List<Contact> searchContactByAddress(String address) {
        List<Contact> contacts = fileService.read();
        try {
            Flowable<Contact> contactFlowable = Flowable.create(emitter -> {
                try {
                    for (Contact contact : contacts) {
                        if (contact.getAddress().equalsIgnoreCase(address)) {
                            emitter.onNext(contact);
                        }
                    }
                    emitter.onComplete();
                } catch (Exception es) {
                    emitter.onError(es);
                }

            }, BackpressureStrategy.BUFFER);

            return contactFlowable
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.single())
                    .toList()
                    .blockingGet();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    @Override
    public List<Contact> searchContactByPhone(String phone) {
        List<Contact> contacts = fileService.read();

        try {
            Flowable<Contact> contactFlowable = Flowable.create(emitter -> {

                try {
                    String cleanPhone = phone.replaceAll("\\D", "");
                    String formattedPhone = "+996 " + cleanPhone.replaceAll("(.{3})(.{3})(.{3})", "$1 $2 $3");

                    for (Contact contact : contacts) {
                        if (contact.getPhone().equalsIgnoreCase(formattedPhone)) {
                            emitter.onNext(contact);
                        }
                    }

                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }, BackpressureStrategy.BUFFER);


            return contactFlowable
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.single())
                    .toList()
                    .blockingGet();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }


}

