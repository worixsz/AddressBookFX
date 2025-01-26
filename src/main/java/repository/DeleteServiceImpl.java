package repository;

import fileService.FileService;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.Contact;
import service.DeleteService;

import java.util.ArrayList;
import java.util.List;

public class DeleteServiceImpl implements DeleteService {

    private final FileService fileService;
    private final List<Contact> contactList;

    public DeleteServiceImpl() {
        this.fileService = new FileService();
        this.contactList = fileService.read() != null ? fileService.read() : new ArrayList<>();
    }

    @Override
    public void deleteContact(List<Contact> contacts) {

        Flowable.create((FlowableEmitter<Void> emitter) -> {
                    try {
                        for (Contact contact : contacts) {
                            contactList.remove(contact);
                        }
                        fileService.write(contactList);
                        emitter.onComplete();

                    } catch (Exception es) {
                        emitter.onError(es);
                    }

                }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(
                        (_) -> System.out.println("Contact success deleted!"),
                        throwable ->
                                System.err.println("Error of deleting contact: " + throwable.getMessage())
                );

    }
}
