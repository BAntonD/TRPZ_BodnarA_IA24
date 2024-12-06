package repository;

public class SettingsSync {

    // Метод для синхронізації налаштувань між таблицями
    public static void syncSettings() {
        // Отримуємо id_Setting з таблиці DefaultSetting
        int defaultIdSetting = DefaultSettingRepository.getDefaultSettingId();

        // Оновлюємо id_Setting в таблиці CurrentSetting
        if (defaultIdSetting != -1) {
            CurrentSettingRepository.updateCurrentSetting(defaultIdSetting);
        } else {
            System.out.println("Error: Default setting ID not found.");
        }
    }
}

