# 🎓 Dự án Quản lý Sinh viên (BTL_QLy_SinhVien)

Dự án **Quản lý Sinh viên** là ứng dụng di động được phát triển trên nền tảng Android (sử dụng ngôn ngữ Java). Ứng dụng cung cấp giải pháp toàn diện hỗ trợ quản lý thông tin đào tạo trong nhà trường, bao gồm quản lý hồ sơ sinh viên, lớp học, khóa học, chuyên ngành, quản lý môn học, nhập/sửa điểm số, đồng thời tích hợp tính năng đăng ký học phần và theo dõi công nợ học phí thông qua hệ thống API RESTful.

---

## 🚀 Tính năng nổi bật

### 1. Hệ thống Quản trị & Xác thực
*   **Đăng nhập / Đăng ký**: Hỗ trợ xác thực tài khoản thông qua API kết nối cơ sở dữ liệu hệ thống.
*   **Menu Điều hướng**: Giao diện chính phân quyền và truy cập nhanh vào các phân hệ quản lý thông qua Dashboard trực quan.

### 2. Quản lý Đào tạo (CRUD & Tìm kiếm)
*   **Quản lý Chuyên ngành**: Xem danh sách, thêm mới, cập nhật thông tin chuyên ngành đào tạo.
*   **Quản lý Khóa học**: Theo dõi các khóa/niên khóa học của trường.
*   **Quản lý Lớp học**: Quản lý lớp hành chính gắn với từng Chuyên ngành và Khóa học cụ thể.
*   **Quản lý Môn học**: Danh mục các môn học, số tín chỉ tương ứng của từng học phần.

### 3. Quản lý Sinh viên chuyên nghiệp
*   **Thông tin chi tiết**: Quản lý mã sinh viên, họ tên, lớp, giới tính, ngày sinh, địa chỉ, số điện thoại.
*   **Lưu trữ hình ảnh**: Hỗ trợ lưu trữ ảnh chân dung trực tiếp dưới dạng `BLOB` trong cơ sở dữ liệu SQLite.
*   **Tìm kiếm thông minh**: Tích hợp thanh tìm kiếm `SearchView` lọc nhanh danh sách sinh viên theo tên hoặc mã số sinh viên theo thời gian thực.

### 4. Quản lý Điểm số & Học tập
*   **Nhập điểm chi tiết**: Quản lý các cột điểm thành phần bao gồm:
    *   Điểm chuyên cần (Điểm CC)
    *   Điểm giữa kỳ (Điểm GK)
    *   Điểm thi kết thúc học phần (Điểm Thi)
*   **Tự động tính toán**: Tự động tính điểm trung bình (Điểm TB) học phần theo công thức cấu hình sẵn.
*   **Xem điểm theo sinh viên**: Lọc và hiển thị bảng điểm chi tiết của từng sinh viên qua giao diện chọn Spinner tiện lợi.

### 5. Phân hệ Sinh viên trực tuyến (API Kết nối)
*   **Đăng ký học phần**: Tra cứu và đăng ký các môn học trực tuyến. Dữ liệu đồng bộ trực tiếp với Server thông qua thư viện mạng **Volley**.
*   **Quản lý Công nợ Học phí**: Xem tổng số tiền học phí cần đóng, số tiền đã đóng và dư nợ hiện tại theo tài khoản đăng nhập của sinh viên.

---

## 📁 Cấu trúc thư mục mã nguồn

Thư mục logic nghiệp vụ chính nằm trong gói `com.example.btl_qly_sinhvien`:

```text
├── Adapter/                  # Các bộ tiếp hợp chuyển đổi dữ liệu hiển thị ListView/RecyclerView
│   ├── Adapter_ChuyenNganh.java
│   ├── Adapter_DK_hocphan.java
│   ├── Adapter_danhsach_congno.java
│   ├── Adapter_Diem.java
│   ├── Adapter_KhoaHoc.java
│   ├── Adapter_Lop.java
│   └── Adapter_SinhVien.java
│
├── Controller/               # Xử lý các nghiệp vụ giao tiếp HTTP API qua mạng
│   └── controll.java         # Chứa logic đăng nhập, đăng ký học phần, tải dữ liệu công nợ bằng Volley
│
├── Database/                 # Quản trị Cơ sở dữ liệu nội bộ
│   └── DataBase.java         # Copy và khởi tạo file SQLite từ thư mục assets vào bộ nhớ ứng dụng
│
├── Model/                    # Định nghĩa các thực thể dữ liệu (Data Models)
│   ├── ChuyenNganh.java
│   ├── Diem.java
│   ├── KhoaHoc.java
│   ├── Lop.java
│   ├── MonHoc.java
│   ├── SinhVien.java
│   ├── model_danhsachcongno.java
│   └── model_dkhp.java
│
├── URL/                      # Cấu hình kết nối mạng
│   └── path.java             # Định nghĩa địa chỉ IP / Base URL của API Server Backend
│
└── [Các Activity].java       # Các màn hình xử lý giao diện UI (MainActivity, Add_*, Sua_*,...)
```

---

## 🗄️ Thiết kế Cơ sở dữ liệu SQLite

Ứng dụng sử dụng cơ sở dữ liệu SQLite cục bộ được lưu trong tệp `btl_qly_sinhvien.sqlite` nằm trong thư mục `assets`. Dưới đây là cấu trúc các bảng chính:

### Bảng Sinh viên (`tbl_sinhvien`)
| Tên cột | Kiểu dữ liệu | Mô tả |
| :--- | :--- | :--- |
| `masv` | TEXT (PK) | Mã số sinh viên |
| `malop` | TEXT (FK) | Mã lớp học |
| `hoten` | TEXT | Họ và tên |
| `gioitinh` | TEXT | Giới tính |
| `ngaysinh` | TEXT | Ngày sinh |
| `diachi` | TEXT | Địa chỉ thường trú |
| `sdt` | TEXT | Số điện thoại |
| `anh` | BLOB | Ảnh chân dung sinh viên |

### Bảng Điểm (`tbl_diem`)
| Tên cột | Kiểu dữ liệu | Mô tả |
| :--- | :--- | :--- |
| `masv` | TEXT (PK, FK) | Mã số sinh viên |
| `mamon` | TEXT (PK, FK) | Mã môn học |
| `diemcc` | TEXT | Điểm chuyên cần |
| `diemgk` | TEXT | Điểm giữa kỳ |
| `diemthi` | TEXT | Điểm thi kết thúc |
| `diemtb` | TEXT | Điểm trung bình môn |

### Các bảng danh mục khác
*   `tbl_chuyennganh`: Lưu trữ thông tin chuyên ngành (`manganh`, `tennganh`).
*   `tbl_khoa`: Lưu trữ niên khóa/khóa học (`makhoa`).
*   `tbl_lop`: Quản lý lớp học hành chính (`malop`, `makhoa`, `manganh`).
*   `tbl_monhoc`: Quản lý danh mục môn học (`mamon`, `tenmon`, `sotc`).
*   `tbl_dangnhap`: Quản lý tài khoản đăng nhập cục bộ (`username`, `password`,...).

---

## 🛠️ Công nghệ sử dụng

*   **Ngôn ngữ lập trình**: Java (Android SDK)
*   **Môi trường phát triển**: Android Studio
*   **Cơ sở dữ liệu nội bộ**: SQLite Database
*   **Thư viện truyền tải mạng**: Volley (sử dụng phương thức POST, định dạng dữ liệu JSON)
*   **Giao diện & UI Components**: Material Design Components (RecyclerView, CardView, FloatingActionButton, Spinners, SearchView...)

---

## ⚙️ Hướng dẫn cài đặt & Chạy ứng dụng

### 1. Chuẩn bị môi trường
*   Cài đặt **Android Studio** phiên bản mới nhất.
*   Cài đặt **JDK 11** trở lên.
*   Thiết bị giả lập Android hoặc thiết bị thật kết nối qua USB Debugging.

### 2. Tải mã nguồn và chạy ứng dụng
1.  **Clone / Tải xuống** thư mục dự án về máy tính.
2.  Mở Android Studio, chọn **File** -> **Open** và tìm đến thư mục `BTL_QLy_SinhVien`.
3.  Đợi Android Studio đồng bộ và tải các thư viện qua **Gradle** (kiểm tra file [build.gradle](file:///e:/BTL_QLy_SinhVien/app/build.gradle) để cấu hình SDK phù hợp nếu cần thiết).
4.  Nhấn nút **Run** (biểu tượng tam giác xanh) để cài đặt ứng dụng lên máy ảo hoặc thiết bị thật.

### 3. Cấu hình kết nối API Backend (Cho chức năng Đăng ký học phần & Công nợ)
Các chức năng này yêu cầu một Web Server chạy backend (ví dụ sử dụng XAMPP chạy PHP & MySQL).
1.  Cài đặt Web Server cục bộ trên máy tính của bạn và cấu hình thư mục chứa API `quanlydaotao/` chứa các file PHP xử lý (`get.php`, `dangkyhocphan.php`, `congno.php`,...).
2.  Tìm địa chỉ IP nội bộ của máy tính chạy server (ví dụ: `192.168.1.5`).
3.  Mở tệp [path.java](file:///e:/BTL_QLy_SinhVien/app/src/main/java/com/example/btl_qly_sinhvien/URL/path.java) và cập nhật đường dẫn tương ứng:
    ```java
    package com.example.btl_qly_sinhvien.URL;
    
    public class path {
        public static String url = "https://<ĐỊA_CHỈ_IP_CỦA_BẠN>/quanlydaotao/";
    }
    ```
4.  Nếu kết nối qua HTTPS nội bộ với chứng chỉ tự ký (Self-signed), ứng dụng đã cấu hình sẵn hàm `turn_off_SSL()` trong [controll.java](file:///e:/BTL_QLy_SinhVien/app/src/main/java/com/example/btl_qly_sinhvien/Controller/controll.java) để bỏ qua kiểm tra SSL nhằm phục vụ mục đích kiểm thử dễ dàng.

---

> [!NOTE]  
> Ứng dụng tự động khởi tạo cơ sở dữ liệu ban đầu bằng cách sao chép file `btl_qly_sinhvien.sqlite` từ thư mục `assets` vào phân vùng lưu trữ riêng của ứng dụng trong lần chạy đầu tiên. Nếu bạn cập nhật dữ liệu trực tiếp trong file sqlite của `assets`, hãy thực hiện **Clear Data/Uninstall** ứng dụng trên thiết bị trước khi chạy lại để cập nhật thay đổi có hiệu lực.
