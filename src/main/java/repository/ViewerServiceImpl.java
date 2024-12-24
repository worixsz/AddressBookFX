package repository;

import fileService.FileService;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.Contact;
import service.ViewerService;

import java.util.List;

public class ViewerServiceImpl implements ViewerService {


    private final FileService fileService;

    public ViewerServiceImpl() {
        fileService = new FileService();


    }

    @Override
    public List<Contact> showContact() {
        List<Contact> contacts = fileService.read();

        return Flowable.fromIterable(contacts)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .toList()
                .blockingGet();
    }

}
