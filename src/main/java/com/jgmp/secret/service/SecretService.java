package com.jgmp.secret.service;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgmp.secret.repo.CipherInfo;
import com.jgmp.secret.repo.CipherInfoRepository;

@Service
public class SecretService {

    @Autowired
    private CipherInfoRepository cipherInfoRepository;

    public String saveToDbAndReturnKey(String text) throws Exception {
        SecretKey key = CipherSerice.generateSecretKey();
        text = text.length() > 500 ? text.substring(0, 500) : text;
        String text2Save = CipherSerice.encryptText(text, key);
        CipherInfo ci = new CipherInfo();
        ci.setText(text2Save);
        CipherInfo res = cipherInfoRepository.save(ci);
        return CipherSerice.encodeKey(key) + "||" + res.getId();
    }

    public String getInfoByKey(String key) throws Exception {
        String[] arr = key.split("\\|\\|");

        CipherInfo cipherInfo = cipherInfoRepository.findById(Integer.valueOf(arr[1]))
                .orElseThrow(() -> new IllegalStateException("SECRET NOT FOUND"));
        SecretKey realKey = CipherSerice.decodeToKey(arr[0]);
        String text = CipherSerice.decryptText(cipherInfo.getText(), realKey);
        cipherInfoRepository.delete(cipherInfo);
        return text;
    }
}
