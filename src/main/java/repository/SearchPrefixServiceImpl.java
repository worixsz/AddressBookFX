package repository;

import fileService.FileService;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.Contact;
import service.SearchPrefixService;

import java.util.List;

public class SearchPrefixServiceImpl implements SearchPrefixService {

    private final FileService fileService;

    public SearchPrefixServiceImpl() {
        this.fileService = new FileService();
    }

    @Override
    public List<Contact> findByNamePrefix(String namePrefix) {
        List<Contact> contacts = fileService.read();

        return Flowable.fromIterable(contacts)
                .filter(contact -> contact.getName().startsWith(namePrefix))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .blockingGet();
    }

    @Override
    public List<Contact> findBySurnamePrefix(String surnamePrefix) {
        List<Contact> contacts = fileService.read();
        return Flowable.fromIterable(contacts)
                .filter(contact -> contact.getSurname().startsWith(surnamePrefix))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .blockingGet();
    }

    @Override
    public List<Contact> findByAddressPrefix(String addressPrefix) {
        List<Contact> contacts = fileService.read();
        return contacts.stream()
                .filter(contact -> contact.getAddress().startsWith(addressPrefix))
                .toList();
    }

    @Override
    public List<Contact> findByPhonePrefix(String phonePrefix) {
        List<Contact> contacts = fileService.read();
        String cleanPhonePrefix = phonePrefix.replace("+996 ", "");

        return contacts.stream()
                .filter(contact -> contact.getPhone().startsWith("+996 " + cleanPhonePrefix))
                .toList();
    }

}
