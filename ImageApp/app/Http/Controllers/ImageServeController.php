<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class ImageServeController extends Controller
{
    public function uploadImage(Request $request)
    {
        if ($request->hasFile('thumbnail')) {
            $thumbnail = $request->file('thumbnail');
            $thumbnailPath = $thumbnail->store('thumbnails', 'public');
            $imageName = asset('images/' . $thumbnailPath);
        }
        return $imageName;
    }
}
