package courses;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CoursesService {

    private ModelMapper modelMapper;
    private CoursesRepository repository;
}
