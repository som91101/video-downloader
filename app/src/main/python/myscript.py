from youtubesearchpython import VideosSearch
import pafy
import os

def init(path):
    os.chdir(path)
    return "working"

def download(song):
    try:
        videoSearch = VideosSearch(song, limit=1)
        b = videoSearch.result()['result']
        url = b[0]['link']
        video = pafy.new(url)

        dl = video.getbest()
        dl.download()
        return "downloaded"
    except:
        return "unsuccesfull"
    

