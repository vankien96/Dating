# Dating
Phân chia công việc
Đề tài: Dating
 

Nhóm sinh viên:		Trương Văn Kiên
				Trần Thông Thành Luân
				Trần Đức Long
				Lưu Thị Ngọc Lan
Link project: https://github.com/vankien96/Dating
Link tài liệu tham khảo : 
-	https://duythanhcse.wordpress.com/lap-trinh-di-dong/android/
-	https://goo.gl/176MW1
-	https://goo.gl/8Nx4xW
Tài khoản Firebase: nguyenvanteo484@gmail.com/teo123456789
I.	Những phần chung tất cả các thành viên đều phải tìm hiểu
-	Các loại layout cơ bản : Linear layout, relative layout, contraitLayout
-	Các component cơ bản : TextView, edittext, button, radioButton,…
-	Toast và AlertDialog
-	Tìm hiểu Share Preferences
-	Các kiểu lập trình sự kiện trong Android
-	Vòng đời của Activity
-	Cách mở màn hình mới (Intent), truyền data qua lại giữa các màn hình
-	Cách sử dụng listView cơ bản , custom listview
-	Nắm sơ về chuỗi JSON.
-	Tìm hiểu về Fragment
-	Mô hình MVC

II.	Phân công chi tiết công việc
1.	Trần Thông Thành Luân

-	   
-	Thiết kế màn hình splash và màn hình Onboarding
-	Các chức năng cần có:
•	Nhấn next sẽ chuyển sang màn hình login
•	Người dùng có thể lướt giữa các màn hình giới thiệu
-	Những thứ cần tìm hiểu:
•	Ở màn hình splash sử dụng Thread để chuyển sang màn hình Main và disable nút back
•	Tìm hiểu Share Preferences để check lần đầu cài app để hiển thị màn hình Onboarding
•	Ở màn hình Onboarding sử dụng ViewPager

2.	Lưu Thị Ngọc Lan
   
-	Thiết kế giao diện màn hình profile , edit profile và setting
-	Các chức năng cần có:
•	Chuyển các màn hình khi nhấn các button 
•	Save được setting của người dùng
•	Load được ảnh từ filebase về
-	Chức năng mở rộng(có thể làm sau):
•	Chọn được ảnh từ thư viện và upload lên firebase
-	Chi tiết công việc:
•	Thiết kế giao diện và set một số action cơ bản
•	Sử dụng Share Preference để lưu setting của người dùng
•	Sử dụng async task để load ảnh
•	Sử dụng Implicit Intent để chọn ảnh từ máy ở màn hình edit

3.	Trần Đức Long
   
-	Thiết kế màn hình Login + Register + Recommendation + Detail 
-	Các chức năng cần có:
•	Đăng nhập và đăng kí với Firebase
•	Đăng nhập với Facebook
•	Load được list những người xung quanh về
-	Chi tiết công việc :
•	Ở màn hình login và register sử dụng Authentication có sẵn của Firebase, Facebook SDK
•	Sử dụng Share Preferences để lưu thông tin đăng nhập của người dùng

4.	Trương Văn Kiên
   
-	Xây dựng giao diện màn hình chat + map
-	Các chức năng cần có:
•	Load được danh sách chat
•	Xây dựng được màn hình chat( bao gồm chat và các tin nhắn trước đó)
•	Xây dựng map dựa trên Google map
•	
-	Chi tiết công việc: 
•	Ở màn hình chat thì sử dụng listview để hiển thị
•	Ở màn hình map sử dụng Google map và xác định vị trí người dùng dựa trên latitude và longitude. Sử dụng marker để đánh dấu vị trí người dùng


