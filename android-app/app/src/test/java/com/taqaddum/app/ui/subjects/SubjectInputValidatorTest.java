package com.taqaddum.app.ui.subjects;
import org.junit.Test;
import static org.junit.Assert.*;
public class SubjectInputValidatorTest {
    @Test public void blankNameIsRejected(){assertFalse(SubjectInputValidator.hasValidName("  "));}
    @Test public void arabicNameIsAccepted(){assertTrue(SubjectInputValidator.hasValidName("الرياضيات"));}
    @Test public void fractionalHoursConvertToMinutes(){assertEquals(Integer.valueOf(90),SubjectInputValidator.hoursToMinutes("1.5"));}
    @Test public void zeroHoursIsRejected(){assertNull(SubjectInputValidator.hoursToMinutes("0"));}
}
