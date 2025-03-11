package org.cwt.task;

import org.cwt.task.dto.BookRentDto;
import org.cwt.task.entity.BookRent;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class BookMapper extends ModelMapper {
    public BookMapper() {
        super();
        this.getConfiguration().setSkipNullEnabled(true);
        this.addMappings(new PropertyMap<BookRent, BookRentDto>() {
            @Override
            protected void configure() {
                map(source.getRentDate(), destination.getRentDate());
                map(source.getReturnDate(), destination.getReturnDate());
                map(source.getUser().getFirstName(), destination.getUserName());
                map(source.getBook().getName(), destination.getBookName());
            }
        });
    }
}
