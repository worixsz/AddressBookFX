package repository;

import fileService.FileService;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.Contact;
import service.CreateService;

import java.util.ArrayList;
import java.util.List;

public class CreateServiceImpl implements CreateService {

    private final FileService fileService;
    private final List<Contact> contactsList;
    private final DataProcessorImpl dataProcessorImpl;

    public CreateServiceImpl() {
        this.fileService = new FileService();
        this.dataProcessorImpl = new DataProcessorImpl();
        this.contactsList = fileService.read() != null ? fileService.read() : new ArrayList<>();

    }


    @Override
    public void createContact(List<Contact> contacts) {
        Flowable.create((FlowableEmitter<Void> emitter) -> {
                    try {
                        for (Contact contact : contacts) {
                            if (contact.getId() == 0) {
                                try {
                                    contact.setId(dataProcessorImpl.generateUniqueId());
                                } catch (Exception idException) {
                                    System.err.println("Error of generating id for contact: " + contact);
                                    continue;
                                }
                            }
                        }
                        contactsList.addAll(contacts);
                        fileService.write(contactsList);

                        emitter.onComplete();
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(
                        (_) -> System.out.println("Contact success created!"),
                        throwable -> System.err.println("Error of creating contact: " + throwable.getMessage())
                );
    }


}

