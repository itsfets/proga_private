package core.builders;

import core.models.standard.StudyGroup;
import core.validators.ValidatorTemplate;

public interface BuilderTemplate<T> {

    T build(ValidatorTemplate<T> validator, int id);

    StudyGroup buildRaw(int id);
}
