package fileService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.Contact;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    private final ObjectMapper objectMapper;

    public FileService() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Реактивный метод для чтения списка контактов из файла.
     * Используется Flowable для выполнения операции в фоновом потоке (Schedulers.io).
     *
     * @return List<Contact> - список контактов.
     */
    public List<Contact> read() {
        try {
            Flowable<Contact> contactFlowable = Flowable.create(emitter -> {
                try {
                    File file = new File("contacts.json");
                    if (!file.exists()) {
                        emitter.onComplete();
                        return;
                    }

                    JsonNode rootNode = objectMapper.readTree(file);
                    JsonNode contactsNode = rootNode.get("contacts");

                    if (contactsNode != null && contactsNode.isArray()) {
                        List<Contact> contacts = objectMapper.convertValue(
                                contactsNode,
                                objectMapper.getTypeFactory().constructCollectionType(List.class, Contact.class)
                        );

                        for (Contact contact : contacts) {
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
            return new ArrayList<>();
        }
    }

    /**
     * Реактивный метод для записи списка контактов в файл.
     * Используется Flowable для выполнения операции в фоновом потоке (Schedulers.io).
     *
     * @param contacts - список контактов.
     */
    public void write(List<Contact> contacts) {
        Flowable.create((FlowableEmitter<Void> emitter) -> {
                    try {
                        File file = new File("contacts.json");

                        ObjectNode rootNode = objectMapper.createObjectNode();
                        rootNode.set("contacts", objectMapper.valueToTree(contacts));

                        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, rootNode);

                        emitter.onComplete();
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(
                        (_) -> System.out.println("Контакты успешно созданы!"),
                        throwable -> System.err.println("Ошибка при создании контактов: " + throwable.getMessage())
                );
    }
}
