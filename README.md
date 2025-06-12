## paslas-backend

**Özet:**
Kullanıcıların lobi (lobby) oluşturup/katılabildiği, yetkili kullanıcıların bu lobilerde etkinlikler (event) tanımlayıp diğer katılımcıların katılım durumunu yönetebildiği, kapasite ve bekleme listesi mekanizmasıyla katılımcı trafiğinin kolayca izlenip yönetilebildiği, başlangıçta halı saha organizasyonları için geliştirilen ancak generic yapısıyla farklı tip etkinliklere de adapte edilebilen bir backend servisi. Frontend için [tıklayınız](https://github.com/SuleymanAliburakCinar/paslas-frontend)

---

## İçindekiler

* [Proje Tanımı](#proje-tanımı)
* [Öne Çıkan Özellikler](#öne-çıkan-özellikler)
* [Kullanılan Teknolojiler ve Öne Çıkan Pratikler](#kullanılan-teknolojiler-ve-öne-çıkan-pratikler)
* [Mimari Yaklaşım ve Katmanlı Yapı](#mimari-yaklaşım-ve-katlanlı-yapı)
* [Önemli Tasarım Notları](#önemli-tasarım-notları)

### Proje Tanımı

Bu projede, kullanıcılar “lobi” kavramı üzerinden organize olurlar:

* Kullanıcılar kendileri lobi oluşturabilir veya başka kullanıcıların oluşturduğu lobilere katılabilir.
* Lobinin yetkilisi (owner veya admin rolündeki kullanıcı) lobide yeni etkinlik (event) oluşturur.
* Lobideki tüm katılımcılar bu etkinliği görebilir, katılım durumlarını (katılacak / katılmayacak) belirtebilir.
* Etkinliğin kapasitesi dolduğunda istekte bulunan kullanıcılar bekleme listesine alınır. Mevcut katılımcıdan birisi katılmayacağını belirtirse, bekleme listesindeki ilk kullanıcı otomatik olarak katılım listesine eklenir.
* Başlangıçta halı saha organizasyonlarını yönetmek amacıyla geliştirilen bu sistem, generic modellere ve esnek mimariye sahip olduğundan benzer “lobby + event + katılım yönetimi” gereksinimi taşıyan farklı senaryolara da kolayca adapte edilebilir.

Bu sayede, hem organizatörler hem de katılımcılar, etkinliklerin yönetimini ve takibini basit bir API üzerinden gerçekleştirebilirler.

---

### Öne Çıkan Özellikler

* **Lobby Yönetimi**: Kullanıcılar yeni lobiler oluşturabilir, var olan lobilere katılabilir veya ayrılabilir.
* **Event Yönetimi**: Yetkili kullanıcılar lobide event tanımlar (oluşturma, güncelleme, silme).
* **Katılım Durumu Takibi**: Kullanıcılar bir etkinliğe “katılacağım” veya “katılmayacağım” şeklinde yanıt verebilir; anlık durum API üzerinden yönetilir.
* **Kapasite ve Bekleme Listesi**: Etkinlik kapasitesi aşıldığında bekleme listesi devreye girer; katılım iptali durumunda bekleme listesindeki sıradaki kullanıcı otomatik eklenir.
* **Generic Tasarım**: Başlangıçta halı saha odaklı olsa da, event modeli ve lobby yapısı soyutlandığından başka etkinlik türlerine de uygun.
* **Katmanlı Mimari ve Temiz Kod Prensipleri**: Controller, Service, Repository katmanları net ayrıştırıldı; SOLID prensiplerine uygun yapı.
* **Global Exception Handling**: Projede özelleştirilmiş `GlobalExceptionHandler` sınıfı ile tutarlı hata formatları sağlanıyor, böylece hata durumlarında frontend veya başka servisler için beklenen JSON yapısı korunuyor.
* **DTO & Mapping**: Entity’lerin doğrudan kullanımı yerine ihtiyaç bazlı DTO’lar tercih edilerek API sürdürülmesi ve versiyon yönetimi kolaylaştırıldı.
* **Validation**: Gerek giriş (request) DTO’larında gerek iş mantığında, Jakarta Validation (örneğin `@Valid`, `@NotNull`, `@Size` vb.) ile güçlü veri doğrulama yapıldı.
* **Transaction Yönetimi**: Kapasite/bekleme listesi güncellemelerinde tutarlı veri sağlamak için transaction yönetimi kullanıldı.
* **Testler**: JUnit, Mockito gibi kütüphaneler kullanıldı.
* **Containerizasyon**: Docker compose dosyası ile veritabanı kolayca ayağa kaldırılabilir.

---

### Kullanılan Teknolojiler ve Öne Çıkan Pratikler

Bu proje modern ve ölçeklenebilir bir Spring Boot altyapısıyla geliştirilmiştir. Aşağıdaki teknolojiler aktif olarak kullanılmıştır:

- **Java 21** – Modern dil özellikleriyle güçlü ve temiz kod yapısı
- **Spring Boot 3.4.5**
  - `spring-boot-starter-web` – RESTful API geliştirme
  - `spring-boot-starter-data-jpa` – ORM ve veri erişimi
  - `spring-boot-starter-security` – Kimlik doğrulama ve yetkilendirme
  - `spring-boot-starter-validation` – Gelişmiş input doğrulama
  - `spring-boot-starter-data-redis` – Redis ile önbellekleme ve oturum yönetimi
  - `spring-boot-starter-test` – Birim ve entegrasyon testleri için altyapı
- **PostgreSQL** – Açık kaynaklı ilişkisel veritabanı yönetim sistemi
- **JWT (io.jsonwebtoken)** – Güvenli token bazlı kimlik doğrulama
- **Lombok** – Boilerplate kodları azaltmak için anotasyon desteği
- **MapStruct** – DTO ve Entity dönüşümleri için compile-time otomatik eşleme
- **Jakarta Mail** – E-posta gönderimi ve bildirim altyapısı
- **Redis** – Hızlı ve etkili cache yönetimi

> * “GlobalExceptionHandler kullanımı sayesinde hata yanıtları tutarlı bir formatta sunuluyor; bu sayede frontend ve diğer tüketiciler hataları kolay yorumlayabiliyor ve kullanıcı deneyimi bozulmuyor.”
> * “DTO mapping süreçlerinde MapStruct tercih edildi; böylece boilerplate kod azalırken, performans ve maintainability avantajı sağlandı.”
> * “Spring Validation anotasyonları ile hem istek düzeyinde hem de iş mantığı seviyesinde güçlü veri doğrulama gerçekleştirildi; hatalı veri akışı baştan engelleniyor.”
> * “Transaction yönetimi özellikle kapasite ve bekleme listesi akışında veri tutarlılığını sağladı; race condition veya tutarsız durumların önüne geçildi.”
> * “Containerization ile projenin farklı ortamlara deploy’u kolaylaştı; Docker Compose dosyası ile veritabanı kolayca ayağa kaldırılabiliyor.”

---

### Mimari Yaklaşım ve Katmanlı Yapı

Bu projede, sürdürülebilirlik, test edilebilirlik ve modülerlik ön planda tutularak **katmanlı mimari (Layered Architecture)** tercih edilmiştir. Bu yapı, iş mantığı ile veriye erişim ve dış dünya arasındaki ayrımı net bir şekilde korumayı hedefler.

---

### Önemli Tasarım Notları

* **Generic Event Modeli:** Event modeli, başlangıçta halı saha organizasyonu için tasarlanmış olsa da entitelerde soyutlamalar (ör. eventType, metadata vs.) konularak farklı etkinlik türlerine kolay adapte edilebilecek şekilde bırakıldı.
* **Bekleme Listesi Mekanizması:** Kapasite dolduğunda kullanıcı otomatik bekleme listesine ekleniyor; iptal gerçekleştiğinde ilk beklemeye alınan kullanıcı hemen katılımcı listesine alınıyor. Bu akış transaction bazlı yönetiliyor.
* **Yetki Kontrolleri:** Sadece lobby sahibi veya belirlenen yetki/grup rolleri event oluşturma/güncelleme/silme işlemlerini gerçekleştirebiliyor. Diğer kullanıcılar salt katılım beyanı ve görüntüleme işlemlerine izinli.
* **Validation:** API’ye gelen veriler `@Valid` anotasyonlarıyla kontrol ediliyor; örneğin event oluştururken kapasite değeri pozitif olmalı, tarih mantığı doğru olmalı (başlangıç\<bitis), kullanıcı kayıtlarında zorunlu alan kontrolleri vb.
* **Exception Handling:** Tüm beklenmedik durumlar veya iş kuralları ihlallerinde (örneğin kapasite aşımı, yetki hatası, geçersiz lobby ID) tutarlı JSON hata yanıtı dönülüyor.
* **Transaction Yönetimi:** Özellikle katılım ve bekleme listesi işlemlerinde @Transactional ile veri tutarlılığı garanti altına alındı.
* **Performans ve Ölçeklenebilirlik Hazırlığı:** Şu anda tek instance yapıda çalışacak şekilde basit; messaging (Kafka vb.) eklemeye uygun soyutlamalar bırakıldı, ancak şu an bu projede kullanılmıyor.
