package com.taqaddum.app.ui.subjects;
public final class SubjectInputValidator {
    private SubjectInputValidator() {}
    public static boolean hasValidName(String name) { return name != null && !name.trim().isEmpty() && name.trim().length() <= 80; }
    public static Integer hoursToMinutes(String hours) {
        try { double value = Double.parseDouble(hours.trim()); if (value <= 0 || value > 168) return null; return (int) Math.round(value * 60); }
        catch (Exception ignored) { return null; }
    }
}
