<?php

namespace App\Entity;

class RestAnswer
{   
    public function __construct()
    {}
    public function sendResponse($status, $data = null, $error = null, $httpStatusCode = 200) {
    
        $response = [
            'status' => $status,
            'data' => $data,
            'error' => $error
        ];
    
        if ($status === 'success') {
            $response['error'] = null;
        }
    
        http_response_code($httpStatusCode);
    
        header('Content-Type: application/json');
        return $response; 
    }
    
}

?>