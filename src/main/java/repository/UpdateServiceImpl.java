package repository;

import fileService.FileService;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.Contact;
import service.UpdateService;

import java.util.ArrayList;
import java.util.List;

public class UpdateServiceImpl implements UpdateService {

    private final FileService fileService;

    public UpdateServiceImpl() {
        this.fileService = new FileService();
    }

    @Override
    public void update(Contact oldContact, Contact newContact) {
        List<Contact> contacts = fileService.read();
        Flowable.create((FlowableEmitter<Void> emitter) -> {
                    try {
                        newContact.setId(oldContact.getId());
                        contacts.remove(oldContact);
                        contacts.add(newContact);
                        fileService.write(contacts);
                        emitter.onComplete();
                    } catch (Exception e) {
                        emitter.onError(e);

                    }
                }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io()).
                observeOn(Schedulers.single())
                .subscribe(
                        (_) -> System.out.println("Contact success updated!"),
                        throwable -> System.err.println("Error of updating contact: " + throwable.getMessage())
                );


    }


    @Override
    public List<Contact> findAllByName(String name) {
        List<Contact> contacts = fileService.read();

        return Flowable.fromIterable(contacts)
                .filter(contact -> contact.getName().equalsIgnoreCase(name))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .blockingGet();

    }

    @Override
    public List<Contact> findAllBySurname(String surname) {
        List<Contact> contacts = fileService.read();

        return Flowable.fromIterable(contacts)
                .filter(contact -> contact.getSurname().equalsIgnoreCase(surname))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .blockingGet();
    }

    @Override
    public List<Contact> findAllByAddress(String address) {
        List<Contact> contacts = fileService.read();

        return Flowable.fromIterable(contacts)
                .filter(contact -> contact.getAddress().equalsIgnoreCase(address))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .blockingGet();
    }

//    @Override
//    public List<Contact> findAllByPhone(String phone) {
//        List<Contact> contacts = fileService.read();
//        String cleanPhone = phone.replaceAll("\\s", "");
//
//        return Flowable.fromIterable(contacts)
//                .filter(contact -> contact.getPhone().replaceAll("\\s", "").equalsIgnoreCase(cleanPhone))
//                .toList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.single())
//                .blockingGet();
//
//    }


}
