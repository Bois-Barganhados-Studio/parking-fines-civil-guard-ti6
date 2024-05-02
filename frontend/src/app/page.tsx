"use client";
import { Button } from "@/components/ui/button";
import { Icon } from "@/components/ui/icon";
import { Loading } from "@/components/ui/loading";
import usePermission from "@custom-react-hooks/use-permission";
import { Camera } from "lucide-react";

import { useEffect, useRef, useState } from "react";

export default function Home() {
  const videoRef = useRef<HTMLVideoElement>(null);
  const { state, error } = usePermission("camera");

  const [sending, setSending] = useState(false);

  useEffect(() => {
    if (state === "granted" && videoRef.current) {
      navigator.mediaDevices
        .getUserMedia({ video: true })
        .then((stream) => {
          videoRef.current!.srcObject = stream;
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [state]);

  return (
    <main className="flex w-screen h-screen flex-col items-center justify-center">
      {state !== "granted" ? (
        <div className="flex flex-col items-center">
          <h1 className="text-4xl font-bold text-center">
            {state === "prompt"
              ? "Aguardando permissão de câmera..."
              : "Permissão negada"}
          </h1>
          {error && <p className="text-destructive">{error}</p>}
        </div>
      ) : (
        <>
          <video
            ref={videoRef}
            className="absolute w-full h-full object-cover"
            autoPlay
            playsInline
            muted
            onCanPlay={() => {
              if (videoRef.current) {
                videoRef.current.play();
              }
            }}
          />
          <Button
            className="absolute bottom-4 w-16 h-16 shadow-lg"
            size="icon"
            disabled={sending}
            onClick={() => {
              setSending(true);
              const canvas = document.createElement("canvas");
              const context = canvas.getContext("2d");
              if (videoRef.current && context) {
                canvas.width = videoRef.current.videoWidth;
                canvas.height = videoRef.current.videoHeight;
                context.drawImage(
                  videoRef.current,
                  0,
                  0,
                  videoRef.current.videoWidth,
                  videoRef.current.videoHeight,
                );
                canvas.toBlob((blob) => {
                  if (blob) {
                    const formData = new FormData();
                    formData.append("file", blob, "photo.png");
                    fetch(`${
                      process.env.API_URL
                    }/api/rotary-parking`, {
                      method: "POST",
                      body: formData,
                    })
                      .then((response) => {
                        if (response.ok) {
                          alert("Foto enviada com sucesso!");
                        } else {
                          alert("Erro ao enviar foto");
                        }
                      })
                      .catch((error) => {
                        console.error(error);
                        alert("Erro ao enviar foto");
                      })
                      .finally(() => {
                        setSending(false);
                      });
                  }
                });
              }
            }}
          >
            {sending ? <Loading size="md" /> : <Icon i={Camera} size="md" />}
          </Button>
        </>
      )}
    </main>
  );
}
