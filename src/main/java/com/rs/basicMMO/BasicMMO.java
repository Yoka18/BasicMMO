package com.rs.basicMMO;

import org.bukkit.plugin.java.JavaPlugin;

public final class BasicMMO extends JavaPlugin {

    @Override
    public void onEnable(){
        // Eğer config dosyası yoksa oluşturur
        saveDefaultConfig();
        getLogger().info("BasicMMO başarıyla yüklendi.");

        // Komutları kayıt ediyoruz
        getCommand("chooseclass").setExecutor(new ClassCommandExecutor(this));
        //getCommand("levelup").setExecutor(new LevelUpCommandExecutor(this));
        getCommand("levelinfo").setExecutor(new XPInfoCommandExecutor(this));
        getCommand("classreload").setExecutor(new ClassReloadCommandExecutor(this));
        getCommand("takexp").setExecutor(new TakeXpCommandExecutor(this));

        // XP event listener'larını kaydediyoruz
        getServer().getPluginManager().registerEvents(new XPBlockListener(this), this);
        getServer().getPluginManager().registerEvents(new XPFishListener(this), this);
        getServer().getPluginManager().registerEvents(new TimbermanListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnListener(this), this);
        getCommand("basicMMO").setExecutor(new HelpCommandExecutor(this));



    }

    @Override
    public void onDisable() {
        // Kapanmadan önce yapılandırma dosyasını kaydet
        saveConfig();

        // Bu plugin tarafından planlanmış tüm görevleri iptal et
        getServer().getScheduler().cancelTasks(this);

        // Kapanma mesajını log'a yazdır
        getLogger().info("ClassPlugin başarıyla kapatıldı.");
    }
}
