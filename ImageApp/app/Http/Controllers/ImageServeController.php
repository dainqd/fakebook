<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class ImageServeController extends Controller
{
    public function uploadImageMain(Request $request)
    {
        $imageName = null;
        if ($request->hasFile('thumbnail')) {
            $thumbnail = $request->file('thumbnail');
            $thumbnailPath = $thumbnail->store('images/thumbnails', 'public');
            $imageName = asset('storage/' . $thumbnailPath);
        }
        return $imageName;
    }

    public function uploadImage()
    {
        return view('welcome');
    }
}
