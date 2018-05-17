# English-Vietnamese Instant Dictionary
Chức năng chính của ứng dụng là cho phép người dùng tra từ tiếng Anh chỉ bằng cách copy từ đó, và nghĩa tiếng Việt của từ sẽ ngay lập tức hiển thị trong notification.

## Project specs
Android Studio 3.1.2

Gradle version 4.5.1

Target SDK: API 25
because background services are limited since API 26; will improve this in the future.

Testing in physical device running Android 7.1.1.

## Roadmap
Các tính năng của ứng dụng sẽ được hiện thực theo thứ tự:
1. Hiển thị nghĩa tiếng Việt của từ tiếng Anh vừa được người dùng copy trên notification.
2. Cho phép người dùng tạo danh sách nghĩa của từ hoặc cụm từ theo ý muốn. Khi copy 1 từ hoặc cụm từ ứng dụng sẽ tra nghĩa trong danh sách này trước khi gọi API của từ điển.
3. Lưu lại danh sách các từ đã tra cùng với nghĩa của chúng trên thiết bị.
4. Cho phép người dùng tạo account để đồng bộ danh sách từ trên nhiều thiết bị khác nhau.
