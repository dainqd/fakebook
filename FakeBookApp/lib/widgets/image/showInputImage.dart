import 'dart:io';

import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';

class InputImage extends StatefulWidget {
  @override
  _InputImageState createState() => _InputImageState();
}

class _InputImageState extends State<InputImage> {
  XFile? _image; // Biến để lưu trữ tệp tin hình ảnh đã chọn

  void _pickImage() async {
    final picker = ImagePicker();
    XFile? pickedImage = await picker.pickImage(source: ImageSource.gallery);

    if (pickedImage != null) {
      setState(() {
        _image = pickedImage;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      onPressed: _pickImage,
      child: Text('Chọn ảnh'),
    );
  }
}
